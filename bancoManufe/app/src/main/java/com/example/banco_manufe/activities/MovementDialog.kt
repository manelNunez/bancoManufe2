package com.example.banco_manufe.activities


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.banco_manufe.R
import com.example.bancoapiprofe.pojo.Movimiento


class MovementDialog(context: Context, private val movimiento: Movimiento) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_movement)

        val tvImporte: TextView = findViewById(R.id.tvImporte)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val tvFecha: TextView = findViewById(R.id.tvFecha)
        val tvCuentaOrigen: TextView = findViewById(R.id.tvCuentaOrigen)
        val tvCuentaDestino: TextView = findViewById(R.id.tvCuentaDestino)
        val btAceptar: Button = findViewById(R.id.btAceptar)

        // Mostrar los detalles del movimiento
        tvImporte.text = "Importe: ${movimiento.getImporte()} €"
        tvDescripcion.text = "Descripción: ${movimiento.getDescripcion()}"
        tvFecha.text = "Fecha: ${movimiento.getFechaOperacion()}"
        tvCuentaOrigen.text = "Cuenta Origen: ${movimiento.getCuentaOrigen()}"
        tvCuentaDestino.text = "Cuenta Destino: ${movimiento.getCuentaDestino()}"

        // Cerrar el diálogo
        btAceptar.setOnClickListener {
            dismiss()
        }
    }
}
