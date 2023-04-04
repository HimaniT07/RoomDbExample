package com.example.mvvmdemomaster.apiService

import UserListResponse
import com.youngbrains.testandroid.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
@GET("users")
suspend fun getData():Response<UserListResponse>
}