package com.example.banco_manufe.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_manufe.R
import com.example.banco_manufe.databinding.ActivityPasswordBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente

class PasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cliente = intent.getSerializableExtra("Cliente") as Cliente


        binding.btAceptar.setOnClickListener {
            var entradaValida = true

            val password1 = binding.tietCambiocpntrasena1.text.toString()
            val password2 = binding.tietCambiocpntrasena2.text.toString()

            if (password1 != password2) {
                binding.tietCambiocpntrasena2.error = "Las contraseñas no coinciden"
                entradaValida = false
            }else{
                // Asigna la nueva contraseña al cliente
                cliente.setClaveSeguridad(password1)

                // Llama a la función changePassword para actualizar en la BD
                val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
                val resultado = mbo?.changePassword(cliente)

                if (resultado == 1) {
                    binding.mensaje.text = "Contraseña cambiada correctamente"
                } else {
                    binding.mensaje.text = "Error al cambiar la contraseña"
                }
            }

            // Si todas las validaciones pasan, mostrar mensaje
            if (entradaValida) {
                binding.mensaje.text = "Contraseña cambiada correctamente"
            }
        }




        binding.btSalir.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}