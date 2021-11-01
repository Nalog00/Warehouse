package com.example.warehouset.data.login

import com.google.gson.annotations.SerializedName

data class UserLogin(
    @SerializedName("user_name")
    val userName: String,
    val password: String
)
