package com.example.moviesexampleapp.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Settings(
    var type: String,
    var country: String,
    var genre: String,
    var ratingFrom: Int,
    var ratingTo: Int,
    var yearFrom: Int,
    var yearTo: Int
): Parcelable
 fun getDefaultSetting() =  Settings("ALL", "США", "комедия",  0, 10, 1000, 3000)