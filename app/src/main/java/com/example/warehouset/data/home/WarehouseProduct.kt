package com.example.warehouset.data.home

import com.google.gson.annotations.SerializedName

data class WarehouseProduct(
    @SerializedName("branch_name")
    val branchName: String,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_code")
    val barCode: String?,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("product_price")
    val productPrice: String,
    @SerializedName("product_image")
    val productImage: String,
    @SerializedName("cost_product")
    val costProduct: String,
    @SerializedName("extant_was")
    val extantWas: Int,
    @SerializedName("new")
    val new: Int,
    @SerializedName("sold_out")
    val soldOut: Int,
    val remained: Int,
    var quantity: Int
)
