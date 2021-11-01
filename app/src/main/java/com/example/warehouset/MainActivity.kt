package com.example.warehouset

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.warehouset.core.ResourceState
import com.example.warehouset.core.extentions.visibility
import com.example.warehouset.databinding.ActivityMainBinding
import com.example.warehouset.settings.Settings
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val viewModel: MainActivityViewModel by inject()
    private val settings: Settings by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        navController = navHostFragment.navController
//        setupObserverToken()
//        viewModel.updatedToken(Settings.HEADERXRW, Settings.HEADERACCEPT, "Bearer ${settings.token}")
    }
//    private fun setupObserverToken(){
//        viewModel.updatedToken.observe(this,{
//            binding.apply {
//                when (it.status) {
//
//                    ResourceState.LOADING -> {
//                        progressBar.visibility(true)
//                    }
//                    ResourceState.SUCCESS -> {
//                        progressBar.visibility(false)
//                        Toast.makeText(this@MainActivity, "Удачно обновлено", Toast.LENGTH_SHORT).show()
//                    }
//                    ResourceState.ERROR -> {
//                        progressBar.visibility(false)
//                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        })
//    }
//    override fun onBackPressed() {
//        val fragment = navHostFragment.childFragmentManager.fragments[0].childFragmentManager.fragments[0]
//        if (fragment.findNavController().currentDestination?.id == R.id.order) {
//            AlertDialog.Builder(this)
//                .setTitle("Данные не сохранены!")
//                .setMessage("Если есть не отправленные заказы, они не сохраняется")
//                .setPositiveButton("Выйти") { _, _ ->
//                    super.onBackPressed()
//                }
//                .setNegativeButton("Остаться"
//                ) { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .show()
//        } else {
//            super.onBackPressed()
//        }
//
//    }

}
