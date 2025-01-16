package com.example.banco_manufe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_manufe.R
import com.example.banco_manufe.databinding.ItemMovimientoBinding
import com.example.bancoapiprofe.pojo.Movimiento

class MovimientoAdapter(
    private var movimientos: List<Movimiento>,
    private val listener: OnMovementClickListener
) : RecyclerView.Adapter<MovimientoAdapter.ViewHolder>() {

    interface OnMovementClickListener {
        fun onMovementClick(movimiento: Movimiento)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMovimientoBinding.bind(view)

        init {
            itemView.setOnClickListener {
                val movimiento = movimientos[adapterPosition]
                listener.onMovementClick(movimiento)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movimiento, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movimientos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movimiento = movimientos[position]
        with(holder) {
            binding.nombre.text = movimiento.getDescripcion()
            binding.importe.text = movimiento.getImporte().toString()
        }
    }

    fun updateData(newMovimientos: List<Movimiento>) {
        movimientos = newMovimientos
        notifyDataSetChanged()
    }

}
