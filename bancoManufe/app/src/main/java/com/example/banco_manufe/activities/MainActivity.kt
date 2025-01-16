package com.example.banco_manufe.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_manufe.R
import com.example.banco_manufe.databinding.ActivityMainBinding
import com.example.bancoapiprofe.pojo.Cliente
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cliente = intent.getSerializableExtra("Cliente") as Cliente
        binding.tv2.text = cliente.getNombre()

        // Configuración del Toolbar
        setSupportActionBar(binding.appbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.bank_24)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Configuración del NavigationView
        binding.navigationView?.setNavigationItemSelectedListener(this)

        // Configuración del DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.appbar,
            R.string.open_nav, R.string.close_nav
        )
        binding.drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        // Configuración de los botones
        binding.cambiarContrasena.setOnClickListener { navigateToPassword(cliente) }
        binding.transferencias.setOnClickListener { navigateToTransfer(cliente) }
        binding.salir.setOnClickListener { navigateToWelcome() }
        binding.movimientos.setOnClickListener { navigateToMovements(cliente) }
        binding.posicionGlobal.setOnClickListener { navigateToGlobalPosition(cliente) }
        binding.cajeros.setOnClickListener{navigateToAtm(cliente)}

        // Configuración para los insets de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun navigateToGlobalPosition(cliente: Cliente) {
        val intent = Intent(this, GlobalPositionActivity::class.java)
        intent.putExtra("Cliente", cliente)
        startActivity(intent)
    }

    private fun navigateToMovements(cliente: Cliente) {
        val intent = Intent(this, MovementsActivity::class.java)
        intent.putExtra("Cliente", cliente)
        startActivity(intent)
    }

    private fun navigateToTransfer(cliente: Cliente) {
        val intent = Intent(this, TransferActivity::class.java)
        intent.putExtra("Cliente", cliente)
        startActivity(intent)
    }

    private fun navigateToPassword(cliente: Cliente) {
        val intent = Intent(this, PasswordActivity::class.java)
        intent.putExtra("Cliente", cliente)
        startActivity(intent)
    }

    private fun navigateToWelcome() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAtm(cliente: Cliente) {
        val intent = Intent(this, AtmManagementActivity::class.java)
        startActivity(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout?.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val cliente = intent.getSerializableExtra("Cliente") as Cliente
        when (item.itemId) {
            R.id.nav_global_position -> navigateToGlobalPosition(cliente)
            R.id.nav_movements -> navigateToMovements(cliente)
            R.id.nav_transfers -> navigateToTransfer(cliente)
            R.id.nav_change_password -> navigateToPassword(cliente)
            R.id.nav_promotions -> {
                // Lógica para promociones
            }
            R.id.nav_atms -> {
                // Lógica para cajeros
            }
            R.id.nav_sett -> navigateToSettings()
            R.id.nav_exit -> navigateToWelcome()
        }
        binding.drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    private fun enableEdgeToEdge() {

    }
}
