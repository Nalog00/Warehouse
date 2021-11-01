package com.example.warehouset.main.home.basket

import com.example.warehouset.data.home.WarehouseProduct

class Basket {
    private var mutableProduct: MutableList<WarehouseProduct> = mutableListOf()
    val product: List<WarehouseProduct> get() = mutableProduct
    fun setProduct(product: WarehouseProduct, count: Int) {
        product.quantity = count
        mutableProduct.forEachIndexed { _, p ->
            if (p.productId == product.productId) {
                p.quantity= count
                return
            }
        }
        mutableProduct.add(product)
    }
}