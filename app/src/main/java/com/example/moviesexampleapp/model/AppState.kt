package com.example.moviesexampleapp.model

import com.example.moviesexampleapp.model.entities.Movie

sealed class AppState {
    data class SuccessForFirstRequest(val moviesList: List<Movie>?): AppState()
    data class SuccessForUpdateList(val moviesList: List<Movie>): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}
