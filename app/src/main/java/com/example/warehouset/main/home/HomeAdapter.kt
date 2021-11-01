package com.example.warehouset.main.home

import android.opengl.Visibility
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.warehouset.R
import com.example.warehouset.core.extentions.inflate
import com.example.warehouset.core.extentions.onClick
import com.example.warehouset.core.extentions.visibility
import com.example.warehouset.data.home.WarehouseProduct
import com.example.warehouset.databinding.ItemHomeFragmentBinding
import com.example.warehouset.settings.Settings
import com.example.warehouset.settings.Settings.Companion.URL

class HomeAdapter(private val settings: Settings): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    var models: MutableList<WarehouseProduct> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    private var onClickImageView: (productName: String,productImage: String )-> Unit = {_,_->}
    fun onClickedSmallImageProduct(onClickImageView: (productName: String,productImage: String )-> Unit){
        this.onClickImageView = onClickImageView
    }

    private var onClickEditText:(quantity: Int)-> Unit = {}
    fun onClickedEditText(onClickEditText:(quantity: Int)-> Unit){
        this.onClickEditText = onClickEditText
    }
    private var onTextChanged:(quantity: Int, model: WarehouseProduct,pos: Int)-> Unit = {_,_,_->}
    fun editTextChanged(onTextChanged:(quantity: Int, model: WarehouseProduct,pos: Int)->Unit){
        this.onTextChanged = onTextChanged
    }


    inner class HomeViewHolder(private val binding: ItemHomeFragmentBinding): RecyclerView.ViewHolder(binding.root){
        fun populateModel(model: WarehouseProduct,position: Int){
            binding.apply {
                tvProductName.text = model.productName
                tvBarCode.text = model.barCode
                tvQuantityProduct.text = model.remained.toString()+"шт"
                tvPriceProduct.text = model.productPrice+"сум"
                if (settings.role=="Mega admin"){
                    Log.d("rolerr",settings.role)
                tvTextCost.visibility(true)
                tvCostProduct.visibility(true)
                tvCostProduct.text = model.costProduct+"сум"
                }
                else
                {
                    tvTextCost.visibility(false)
                    tvCostProduct.visibility(false)
                }
                Glide.with(binding.root)
                    .load("$URL${model.productImage}")
                    .centerCrop()
                    .into(binding.ivProduct)
                ivProduct.onClick {
                    onClickImageView.invoke(model.productName,model.productImage)
                }
                etEnterQuantityProduct.onClick {
                    onClickEditText.invoke(model.quantity)
                }

                etEnterQuantityProduct.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        onTextChanged.invoke(
                            if (p0.toString().isNotEmpty()) p0.toString().toInt() else 0, model, position)
                    }
                }
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = parent.inflate(R.layout.item_home_fragment)
        val binding = ItemHomeFragmentBinding.bind(itemView)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.populateModel(models[position],position)
    }

    override fun getItemCount(): Int  = models.size

}