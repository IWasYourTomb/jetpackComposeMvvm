package com.example.api_compose.Model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val name: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String
)
