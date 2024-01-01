package com.dicoding.appstory.ui.signup

import androidx.lifecycle.ViewModel
import com.dicoding.appstory.repository.Repository

class SignUpViewModel(private val repository: Repository) : ViewModel() {

    fun register(name: String, email: String, password: String) =repository.register(name,email,password)

}