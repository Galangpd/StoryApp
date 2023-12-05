package com.example.mystoryapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoryapp.response.LoginResponse
import com.example.mystoryapp.repository.Repository
import com.example.mystoryapp.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> get() = _loginResult

    fun loginUser(email: String, password: String) = repository.loginUser(email, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}