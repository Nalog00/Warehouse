package com.example.warehouset.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.warehouset.MainActivity
import com.example.warehouset.R
import com.example.warehouset.core.ResourceState
import com.example.warehouset.core.extentions.onClick
import com.example.warehouset.core.extentions.visibility
import com.example.warehouset.data.login.UserLogin
import com.example.warehouset.databinding.ActivityLoginBinding
import com.example.warehouset.settings.Settings
import com.example.warehouset.settings.Settings.Companion.HEADERACCEPT
import com.example.warehouset.settings.Settings.Companion.HEADERXRW
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity: AppCompatActivity() {
    private val setting: Settings by inject()
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!setting.isFirstLaunched) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            btnSignIn.onClick {
                val username = etLogin.text.toString()
                val password = etPassword.text.toString()
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.signIn(HEADERXRW, HEADERACCEPT, UserLogin(username, password)
                    )
                } else {
                    when {
                        username.isEmpty() -> etLogin.error = getString(R.string.enter_your_login)
                        password.isEmpty() -> etPassword.error =
                            getString(R.string.enter_your_password)
                    }

                }
            }
        }
        setupObserver()
    }
    private fun setupObserver(){
        viewModel.user.observe(this,{
            when(it.status) {
                ResourceState.LOADING -> binding.loading.visibility(true)
                ResourceState.SUCCESS -> {
                    binding.loading.visibility(false)
                    it.data?.let {data->
                        if (data.successful)
                        {
                            val intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                ResourceState.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    binding.loading.visibility(false)
                }
            }
        })
    }
}