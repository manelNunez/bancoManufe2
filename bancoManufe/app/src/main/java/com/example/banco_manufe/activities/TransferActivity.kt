package com.example.banco_manufe.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_manufe.R
import com.example.banco_manufe.databinding.ActivityTransferBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val spCuentas: Spinner = findViewById<Spinner>(R.id.spCuentas)

        val cuentas = resources.getStringArray(R.array.cuentas)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuentas)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spCuentas.adapter = adapter

        spCuentas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val opcionSeleccionada = parent?.getItemAtPosition(position).toString()

                Toast.makeText(
                    this@TransferActivity,
                    "Seleccionaste $opcionSeleccionada",
                    Toast.LENGTH_SHORT
                ).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val radioGroup: RadioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        val rbPropia: RadioButton = findViewById<RadioButton>(R.id.rbPropia)
        val opcion1: Spinner = findViewById<Spinner>(R.id.opcion1)

        val rbAjena: RadioButton = findViewById<RadioButton>(R.id.rbAjena)
        val opcion2: TextInputEditText = findViewById<TextInputEditText>(R.id.opcion2)




        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbPropia -> {
                    opcion1.visibility = View.VISIBLE
                    opcion2.visibility = View.INVISIBLE
                }

                R.id.rbAjena -> {
                    opcion1.visibility = View.INVISIBLE
                    opcion2.visibility = View.VISIBLE
                }
            }


            if (rbPropia.isChecked) {

                opcion1.visibility = View.VISIBLE
                opcion2.visibility = View.GONE


                val cuentas = resources.getStringArray(R.array.cuentas)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuentas)

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                opcion1.adapter = adapter
                opcion1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val opcionSeleccionada = parent?.getItemAtPosition(position).toString()

                        Toast.makeText(
                            this@TransferActivity,
                            "Seleccionaste $opcionSeleccionada",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }


            } else if (rbAjena.isChecked) {
                opcion1.visibility = View.GONE
                opcion2.visibility = View.VISIBLE
            }

        }

        val cancelar: MaterialButton = findViewById<MaterialButton>(R.id.cancelar)
        cancelar.setOnClickListener {
            spCuentas.setSelection(0)
            opcion1.setSelection(0)
            opcion2.setText("")
            val etCantidad: TextInputEditText = findViewById<TextInputEditText>(R.id.etCantidad)
            etCantidad.setText("")
            radioGroup.clearCheck()
            val checkbox: CheckBox = findViewById<CheckBox>(R.id.checkbox)
                checkbox.isChecked = false
        }

        val enviar: MaterialButton = findViewById<MaterialButton>(R.id.enviar)
        enviar.setOnClickListener {

            val cuentaSeleccionada = binding.spCuentas.selectedItem.toString()
            val cantidad = binding.etCantidad.text.toString()
            val tipoCuenta = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.rbPropia -> "A cuenta propia"
                R.id.rbAjena -> "A cuenta ajena"
                else -> {""}
            }
            val cuentaAjena = binding.opcion2.text.toString()
            val justificante = if (binding.checkbox.isChecked) "Enviar justificante" else ""

            Toast.makeText(this@TransferActivity,"Cuenta Origen: $cuentaSeleccionada\n" +
                    "            Tipo de Cuenta: $tipoCuenta\n" +
                    "            Cantidad: $cantidad\n" +
                    "            Cuenta Ajena: $cuentaAjena\n" +
                    "             $justificante",
                Toast.LENGTH_LONG).show()
        }


    }
}