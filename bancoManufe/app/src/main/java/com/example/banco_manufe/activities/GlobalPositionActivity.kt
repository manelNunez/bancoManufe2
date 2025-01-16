package com.example.banco_manufe.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.banco_manufe.R
import com.example.banco_manufe.adapters.CuentasListener
import com.example.banco_manufe.fragments.AccountMovementsFragment
import com.example.banco_manufe.fragments.AccountsFragment
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity(), CuentasListener {

    private var isHorizontal = false
    private var isTabletVertical = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_position)

        isHorizontal = findViewById<View?>(R.id.fragmentContainerDetails) != null

        isTabletVertical = findViewById<View?>(R.id.fragmentContainerMovement) != null

        // Recuperar cliente desde los argumentos
        val cliente = intent.getSerializableExtra("Cliente") as? Cliente

        if (savedInstanceState == null) {
            val accountsFragment = AccountsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("Cliente", cliente)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, accountsFragment)
                .commit()

            if (isHorizontal || isTabletVertical) {
                // Cargar un fragmento de movimientos vacío inicialmente
                loadMovementsFragment(null)
            }
        }
    }

    override fun onCuentaSeleccionada(cuenta: Cuenta) {
        loadMovementsFragment(cuenta)
    }

    private fun loadMovementsFragment(cuenta: Cuenta?) {
        val accountMovementsFragment = AccountMovementsFragment().apply {
            arguments = Bundle().apply {
                putSerializable("Cuenta", cuenta)
            }
        }

        when {
            isHorizontal -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerDetails, accountMovementsFragment)
                    .commit()
            }
            isTabletVertical -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerMovement, accountMovementsFragment)
                    .commit()
            }
            else -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, accountMovementsFragment)
                    .addToBackStack(null) // Permitir volver
                    .commit()
            }
        }
    }
}
