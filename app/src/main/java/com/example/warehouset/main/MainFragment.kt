package com.example.warehouset.main

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.warehouset.MainActivity
import com.example.warehouset.R
import com.example.warehouset.databinding.FragmentMainBinding
import com.example.warehouset.settings.Settings
import org.koin.android.ext.android.inject

class MainFragment: Fragment(R.layout.fragment_main) {
    private val setting: Settings by inject()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding:FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setting.isFirstLaunched = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentMainBinding.bind(view)
        navController = requireActivity().findNavController(R.id.main_nav_graph)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.historyTransaction, R.id.nav_info
            ), binding.drawerLayout
        )
        binding.navView.setupWithNavController(navController)
        binding.appBarMain.toolbar.setNavigationOnClickListener {
            navController.navigateUp(appBarConfiguration)
        }
    }
}