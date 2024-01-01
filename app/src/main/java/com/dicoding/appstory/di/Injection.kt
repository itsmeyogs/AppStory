package com.dicoding.appstory.di

import android.content.Context
import com.dicoding.appstory.data.local.pref.UserPreference
import com.dicoding.appstory.data.local.pref.dataStore
import com.dicoding.appstory.data.remote.retrofit.ApiConfig
import com.dicoding.appstory.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(pref, apiService)
    }
}