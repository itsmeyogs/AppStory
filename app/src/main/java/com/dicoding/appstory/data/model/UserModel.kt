package com.dicoding.appstory.data.model

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)