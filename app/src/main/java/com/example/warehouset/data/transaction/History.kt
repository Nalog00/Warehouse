package com.example.warehouset.data.transaction

import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("branch_name")
    val branchName: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("transaction_count")
    val transactionCount: Int,
    @SerializedName("transaction_price")
    val transactionPrice: Int,
    val from: String,
    val to: String
)
