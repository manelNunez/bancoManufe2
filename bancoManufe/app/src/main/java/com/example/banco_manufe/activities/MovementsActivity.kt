package com.example.banco_manufe.activities

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_manufe.R
import com.example.banco_manufe.adapters.MovimientoAdapter
import com.example.banco_manufe.databinding.ActivityMovementsBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta
import com.example.bancoapiprofe.pojo.Movimiento

class MovementsActivity : AppCompatActivity(), MovimientoAdapter.OnMovementClickListener {

    private lateinit var context: Context
    private lateinit var movimientoAdapter: MovimientoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration

    private lateinit var binding: ActivityMovementsBinding
    private lateinit var listaCuentas: List<Cuenta>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar y configurar el binding
        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cliente = intent.getSerializableExtra("Cliente") as Cliente
        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        listaCuentas = mbo?.getCuentas(cliente) as? ArrayList<Cuenta> ?: listOf()

        val cuentas = listaCuentas.map { cuenta ->
            "${cuenta.getBanco()}-${cuenta.getSucursal()}-${cuenta.getDc()}-${cuenta.getNumeroCuenta()}"
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuentas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCuentas.adapter = adapter  // Usar binding para el spinner

        linearLayoutManager = LinearLayoutManager(this)
        binding.reciclerView.layoutManager = linearLayoutManager

        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.reciclerView.addItemDecoration(itemDecoration)

        binding.spCuentas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val cuentaSeleccionada = listaCuentas[position]

                val movimientos = getMovimientos(cuentaSeleccionada)

                movimientoAdapter = MovimientoAdapter(movimientos, this@MovementsActivity)
                binding.reciclerView.adapter = movimientoAdapter
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getMovimientos(cuenta: Cuenta): MutableList<Movimiento> {
        val mbo = MiBancoOperacional.getInstance(this)
        return mbo?.getMovimientos(cuenta) as? MutableList<Movimiento> ?: arrayListOf()
    }

    override fun onMovementClick(movimiento: Movimiento) {
        // Crear el diálogo personalizado
        val dialog = MovementDialog(this, movimiento)

        // Mostrar el diálogo
        dialog.show()
    }
}
