package com.example.moviesexampleapp.model.repository

import com.example.moviesexampleapp.model.entities.Movie
import com.example.moviesexampleapp.model.entities.rest.rest_entities.main_list.MoviesListDTO
import com.example.moviesexampleapp.model.entities.rest.rest_entities.video.VideoResponseItem

interface Repository {
    fun getMoviesFromServer(country: String, genre: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int, yearTo: Int, type: String, page: Int): ArrayList<Movie>?
    fun getFakeMoviesFromLocalStorage(moviesListDTO: MoviesListDTO?): ArrayList<Movie>
    fun getMoviesFromLocalStorage(): List<Movie>
    fun getTrailer(id: Int): VideoResponseItem?
    fun getMoreMovies(moviesListDTO: MoviesListDTO, from: Int): List<Movie>
    fun getWantedMovies(): List<Movie>
    fun getViewedMovies(): List<Movie>
    fun saveEntity(movie: Movie)
    fun updateEntity(movie: Movie)
    fun deleteEntity(id: Int)
    fun deleteAll()
}