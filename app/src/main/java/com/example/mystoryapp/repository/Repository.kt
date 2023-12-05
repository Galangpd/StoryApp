package com.example.mystoryapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mystoryapp.Paging
import com.example.mystoryapp.response.LoginResponse
import com.example.mystoryapp.Preference
import com.example.mystoryapp.response.RegisterResponse
import com.example.mystoryapp.UserModel
import com.example.mystoryapp.api.ApiConfig
import com.example.mystoryapp.api.ApiService
import com.example.mystoryapp.Result
import com.example.mystoryapp.response.ListStoryItem
import com.example.mystoryapp.response.StoryResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class Repository private constructor(
    private val userPreference: Preference,
    private val apiService: ApiService
){

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    fun loginUser(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val success = apiService.login(email,password)
            emit(Result.Success(success))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(error.message?.let { Result.Error(it) })
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getUser()
    }

    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                Paging(userPreference, apiService)
            }
        ).liveData
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            userPreference: Preference,
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreference, apiService)
            }.also { instance = it }
    }
}