package com.example.warehouset.main.home.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouset.R
import com.example.warehouset.core.extentions.inflate
import com.example.warehouset.data.home.WarehouseProduct
import com.example.warehouset.databinding.ItemBasketDialogBinding

class BasketAdapter: RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    var models: MutableList<WarehouseProduct> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class BasketViewHolder(private val binding: ItemBasketDialogBinding): RecyclerView.ViewHolder(binding.root){
        fun populateModel(model: WarehouseProduct){
            binding.apply {
                tvProductName.text = model.productName
                tvCount.text = model.quantity.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val itemView = parent.inflate(R.layout.item_basket_dialog)
        val binding = ItemBasketDialogBinding.bind(itemView)
        return BasketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int = models.size
}