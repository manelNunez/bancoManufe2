package com.example.banco_manufe.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_manufe.adapter.AccountsAdapter
import com.example.banco_manufe.adapters.CuentasListener
import com.example.banco_manufe.databinding.FragmentAccountsBinding
import com.example.bancoapiprofe.bd.MiBancoOperacional
import com.example.bancoapiprofe.pojo.Cliente
import com.example.bancoapiprofe.pojo.Cuenta

class AccountsFragment : Fragment(), AccountsAdapter.OnClickListener {

    private lateinit var cuentaAdapter: AccountsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: FragmentAccountsBinding
    private var listener: CuentasListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountsBinding.inflate(inflater, container, false)

        // Recuperar el cliente de los argumentos
        val cliente = arguments?.getSerializable("Cliente") as? Cliente

        if (cliente != null) {
            // Obtener las cuentas del cliente
            val cuentas = getCuentas(cliente)
            cuentaAdapter = AccountsAdapter(cuentas, this)

            linearLayoutManager = LinearLayoutManager(requireContext())
            binding.reciclerView.layoutManager = linearLayoutManager
            binding.reciclerView.adapter = cuentaAdapter
            binding.reciclerView.addItemDecoration(DividerItemDecoration(requireContext(), linearLayoutManager.orientation))
        }

        return binding.root
    }

    override fun onClick(cuenta: Cuenta) {
        listener?.onCuentaSeleccionada(cuenta)
    }

    private fun getCuentas(cliente: Cliente): List<Cuenta> {
        val mbo = MiBancoOperacional.getInstance(requireContext())
        return mbo?.getCuentas(cliente) as? ArrayList<Cuenta> ?: listOf()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CuentasListener) {
            listener = context
        } else {
            throw RuntimeException("$context debe implementar CuentasListener")
        }
    }
}
