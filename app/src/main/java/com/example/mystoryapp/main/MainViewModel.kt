package com.example.mystoryapp.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mystoryapp.UserModel
import com.example.mystoryapp.api.ApiConfig
import com.example.mystoryapp.repository.Repository
import com.example.mystoryapp.response.ListStoryItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getStory(token: String):LiveData<PagingData<ListStoryItem>> = repository.getStory().cachedIn(viewModelScope)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}