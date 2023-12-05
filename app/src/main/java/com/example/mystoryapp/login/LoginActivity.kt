package com.example.mystoryapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mystoryapp.R
import com.example.mystoryapp.UserModel
import com.example.mystoryapp.databinding.ActivityLoginBinding
import com.example.mystoryapp.Result
import com.example.mystoryapp.ViewModelFactory
import com.example.mystoryapp.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAction()
    }

    private fun setUpAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.loginUser(email, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            result.data.message.let {
                                Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                                viewModel.saveSession(UserModel(email, "${result.data.loginResult?.token}"))
                                Log.d("token", "setupAction: ${result.data.loginResult?.token}")
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                        is Result.Error -> {
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}