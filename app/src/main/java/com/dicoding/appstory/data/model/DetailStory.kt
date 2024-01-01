package com.dicoding.appstory.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailStory(
    var name: String,
    var description: String,
    var photoUrl: String
) : Parcelable