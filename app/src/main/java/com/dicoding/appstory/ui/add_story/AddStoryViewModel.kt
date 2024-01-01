package com.dicoding.appstory.ui.add_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.appstory.data.model.UserModel
import com.dicoding.appstory.repository.Repository
import java.io.File

class AddStoryViewModel(private val repository: Repository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun uploadImage(token:String, file: File, description: String) = repository.uploadImage(token,file, description)
}