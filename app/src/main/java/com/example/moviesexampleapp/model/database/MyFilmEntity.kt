package com.example.moviesexampleapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesexampleapp.model.entities.MovieState


@Entity
data class MyFilmEntity(
    @PrimaryKey (autoGenerate = true) val id: Long,
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val shortDescription: String?,
    val description: String?,
    val year: Int?,
    val posterUrl: String?,
    val rating: Double?,
    val webUrl: String?,
    val state: MovieState
)
