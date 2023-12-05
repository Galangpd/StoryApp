package com.example.mystoryapp.di

import android.content.Context
import com.example.mystoryapp.Preference
import com.example.mystoryapp.api.ApiConfig
import com.example.mystoryapp.dataStore
import com.example.mystoryapp.repository.Repository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository {
    val pref = Preference.getInstance(context.dataStore)
    val user = runBlocking { pref.getUser().first() }
    val apiService = ApiConfig.getApiService(user.token)
    return Repository.getInstance(pref,apiService)
    }
}