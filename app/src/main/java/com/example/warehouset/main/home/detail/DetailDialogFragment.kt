package com.example.warehouset.main.home.detail

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.warehouset.databinding.DetailDialogBinding
import com.example.warehouset.settings.Settings.Companion.URL

class DetailDialogFragment(context: Context): Dialog(context) {

    private lateinit var binding: DetailDialogBinding
    var imageUrl: String = ""
    var productName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            Glide.with(binding.root)
                .load("$URL${imageUrl}")
                .into(bigImageProduct)
            tvProductName.text = productName
        }
    }

}