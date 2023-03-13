package com.example.moviesexampleapp.model.entities.rest

import com.example.moviesexampleapp.model.entities.rest.rest_entities.details.MovieDetailsDTO
import com.example.moviesexampleapp.model.entities.rest.rest_entities.filters.FiltersDTO
import com.example.moviesexampleapp.model.entities.rest.rest_entities.main_list.MoviesListDTO
import com.example.moviesexampleapp.model.entities.rest.rest_entities.similars.RelatedMoviesListDTO
import com.example.moviesexampleapp.model.entities.rest.rest_entities.video.VideoDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("films")
    fun getMovies(
        @Query("countries") countries: List<Int>,
        @Query("genres") genres: List<Int>,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("page") page: Int
    ) : Call<MoviesListDTO>

    @GET("films/{id}")
    fun getFilmDetails(@Path(value = "id") id: Int
    ): Call<MovieDetailsDTO>

    @GET("films/filters")
    fun getFilters(): Call<FiltersDTO>

    @GET("films/{id}/similars")
    fun getRelatedFilms(@Path(value = "id") id: Int
    ): Call<RelatedMoviesListDTO>

    @GET("films/{id}/videos")
    fun getVideo(@Path(value = "id") id: Int
    ): Call<VideoDTO>

}