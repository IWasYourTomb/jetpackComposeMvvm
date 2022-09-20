package com.example.api_compose.Service

import com.example.api_compose.Model.User
import retrofit2.http.GET

interface UserService {
    @GET("/users")
    suspend fun fetchUser(): List<User>
}