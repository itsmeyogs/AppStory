package com.dicoding.appstory.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
    val token: String
): Parcelable