package com.example.warehouset.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.warehouset.R
import com.example.warehouset.settings.Settings
import org.koin.android.ext.android.inject

class MainFragment: Fragment(R.layout.fragment_main) {
    private val setting: Settings by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setting.isFirstLaunched = false
    }
}