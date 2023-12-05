package com.example.mystoryapp.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mystoryapp.ViewModelFactory
import com.example.mystoryapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAction()
    }

    private fun setUpAction(){
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    binding.passwordEditText.setError(
                        "Password tidak boleh kurang dari 8 karakter",
                        null
                    )
                } else {
                    binding.passwordEditText.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })

        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // Validasi input
            if (name.isNotEmpty() && email.isNotEmpty() && password.length >= 8) {
                // Memulai proses registrasi melalui ViewModel
                viewModel.registerUser(name, email, password)
            } else {
                // Menampilkan pesan error jika input tidak valid
                Toast.makeText(
                    this,
                    "Harap isi semua kolom",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.registrationResult.observe(this) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
        }
    }
}