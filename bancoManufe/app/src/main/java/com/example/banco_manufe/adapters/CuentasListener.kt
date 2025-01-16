package com.example.banco_manufe.adapters

import com.example.bancoapiprofe.pojo.Cuenta

interface CuentasListener {
    fun onCuentaSeleccionada(cuenta: Cuenta)
}
