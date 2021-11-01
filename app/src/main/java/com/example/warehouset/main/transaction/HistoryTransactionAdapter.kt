package com.example.warehouset.main.transaction

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouset.R
import com.example.warehouset.core.extentions.inflate
import com.example.warehouset.core.extentions.visibility
import com.example.warehouset.data.transaction.History
import com.example.warehouset.databinding.ItemHistoryTransactionBinding
import com.example.warehouset.settings.Settings

class HistoryTransactionAdapter(private val settings: Settings): RecyclerView.Adapter<HistoryTransactionAdapter.HistoryViewHolder>() {

    var models: MutableList<History> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    inner class HistoryViewHolder(private val binding:ItemHistoryTransactionBinding): RecyclerView.ViewHolder(binding.root){
        fun populateModel(model: History){
            binding.apply {
                tvProductName.text = model.productName
                tvFrom.text = model.from
                tvTo.text = model.to
                tvProductQuantity.text = model.transactionCount.toString()
                if (settings.role=="Mega admin")
                    {
                        tvTextCost.visibility(true)
                        tvCostProduct.visibility(true)
                        tvCostProduct.text = model.transactionPrice.toString()
                    }
                else
                {
                    tvTextCost.visibility(false)
                    tvCostProduct.visibility(false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
     val itemView = parent.inflate(R.layout.item_history_transaction)
     val binding = ItemHistoryTransactionBinding.bind(itemView)
     return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
    holder.populateModel(models[position])
    }

    override fun getItemCount(): Int = models.size
}