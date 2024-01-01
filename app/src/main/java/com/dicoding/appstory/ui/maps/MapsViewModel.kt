package com.dicoding.appstory.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.appstory.data.model.UserModel
import com.dicoding.appstory.repository.Repository

class MapsViewModel(private val repository: Repository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun getStoryWithLocation(token:String) = repository.getStoryWithLocation(token)
}