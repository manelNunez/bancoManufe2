package com.example.banco_manufe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_manufe.R
import com.example.banco_manufe.adapters.MovimientoAdapter
import com.example.banco_manufe.databinding.FragmentAccountMovementsBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cuenta
import com.example.bancoapiprofe.pojo.Movimiento

class AccountMovementsFragment : Fragment() {

    private lateinit var movimientoAdapter: MovimientoAdapter
    private lateinit var binding: FragmentAccountMovementsBinding
    private var movimientos: List<Movimiento> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountMovementsBinding.inflate(inflater, container, false)

        val cuenta = arguments?.getSerializable("Cuenta") as? Cuenta

        if (cuenta != null) {
            movimientos = getMovimientos(cuenta)
            setupRecyclerView()
            setupBottomNavigation()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        movimientoAdapter = MovimientoAdapter(movimientos, object : MovimientoAdapter.OnMovementClickListener {
            override fun onMovementClick(movimiento: Movimiento) {
                // Acción al hacer clic en un movimiento
            }
        })

        binding.reciclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movimientoAdapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_todos -> {
                    updateMovementsList(movimientos)
                    true
                }
                R.id.nav_tipo_0 -> {
                    val filtered = movimientos.filter { it.getTipo() == 0 }
                    updateMovementsList(filtered)
                    true
                }
                R.id.nav_tipo_1 -> {
                    val filtered = movimientos.filter { it.getTipo() == 1 }
                    updateMovementsList(filtered)
                    true
                }
                R.id.nav_tipo_2 -> {
                    val filtered = movimientos.filter { it.getTipo() == 2 }
                    updateMovementsList(filtered)
                    true
                }
                else -> false
            }
        }
    }


    private fun updateMovementsList(filteredMovements: List<Movimiento>) {
        movimientoAdapter.updateData(filteredMovements)
    }

    private fun getMovimientos(cuenta: Cuenta): List<Movimiento> {
        val mbo = MiBancoOperacional.getInstance(requireContext())
        return mbo?.getMovimientos(cuenta) as? MutableList<Movimiento> ?: arrayListOf()
    }
}
