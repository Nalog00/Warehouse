package com.example.warehouset.main.home.basket

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.warehouset.core.extentions.onClick
import com.example.warehouset.data.Transaction
import com.example.warehouset.data.home.WarehouseProduct
import com.example.warehouset.databinding.BasketDialogBinding

class BasketDialog(context: Context): Dialog(context) {
    private lateinit var binding: BasketDialogBinding
    private val adapter = BasketAdapter()
    var list: MutableList<WarehouseProduct> = mutableListOf()

    private var orders:(list:List<Transaction>)->Unit={}
    fun onClickOrders(orders:(list:List<Transaction>)->Unit){
        this.orders=orders
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BasketDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            rvList.adapter = adapter
            adapter.models = list
            rvList.addItemDecoration(
                DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL)
            )
            val ordersList: MutableList<Transaction> = mutableListOf()
            list.forEach {
                ordersList.add(Transaction(it.productId,it.quantity))
            }
            btnFinish.onClick {
                orders.invoke(ordersList)
                dismiss()
            }

        }
    }

}