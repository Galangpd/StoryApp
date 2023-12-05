package com.example.mystoryapp.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoryapp.response.RegisterResponse
import com.example.mystoryapp.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    private val _registrationResult = MutableLiveData<RegisterResponse>()
    val registrationResult: LiveData<RegisterResponse> get() = _registrationResult

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = repository.register(name, email, password)
                _registrationResult.value = result
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Error registering user: ${e.message}")
            }
        }
    }
}