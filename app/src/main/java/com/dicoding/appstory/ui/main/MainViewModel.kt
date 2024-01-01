package com.dicoding.appstory.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.appstory.data.model.UserModel
import com.dicoding.appstory.data.remote.response.ListStoryItem
import com.dicoding.appstory.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> {
        return repository.getStory(token).cachedIn(viewModelScope)
    }


}