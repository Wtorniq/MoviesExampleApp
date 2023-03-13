package com.example.moviesexampleapp.model.repository

import com.example.moviesexampleapp.model.database.Database
import com.example.moviesexampleapp.model.database.MyFilmEntity
import com.example.moviesexampleapp.model.entities.Movie
import com.example.moviesexampleapp.model.entities.MovieState
import com.example.moviesexampleapp.model.entities.getErrorMovie
import com.example.moviesexampleapp.model.entities.rest.MoviesRepo
import com.example.moviesexampleapp.model.entities.rest.rest_entities.details.getMovieDetailsDTO
import com.example.moviesexampleapp.model.entities.rest.rest_entities.filters.FiltersDTO
import com.example.moviesexampleapp.model.entities.rest.rest_entities.main_list.MoviesListDTO
import com.example.moviesexampleapp.model.entities.rest.rest_entities.video.VideoResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl : Repository {
    private var finalMoviesList = ArrayList<Movie>()
    override fun getMoviesFromServer(country: String, genre: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int, yearTo: Int, type: String, page: Int): ArrayList<Movie> {

        var dtoFilters: FiltersDTO? = null
        try {
            dtoFilters = MoviesRepo.api.getFilters().execute().body()
        } catch (e: Throwable){
            e.printStackTrace()
        }
        var countryIdList: List<Int> = listOf()
        var genreIdList: List<Int> = listOf()
        dtoFilters?.countries?.forEach {
            if (it.country == country){
                countryIdList = listOf(it.id)
            }
        }
        dtoFilters?.genres?.forEach {
            if (it.genre == genre) {
                genreIdList = listOf(it.id)
            }
        }

        val moviesList: MoviesListDTO?
        try {
            moviesList = MoviesRepo.api.getMovies(
                countryIdList,
                genreIdList,
                type,
                ratingFrom,
                ratingTo,
                yearFrom,
                yearTo,
                page
            ).execute().body()
        } catch (e: Throwable){
            e.printStackTrace()
            return arrayListOf(getErrorMovie())
        }
        return getMoviesList(moviesList)
    }


    private fun getMoviesList(moviesListDTO: MoviesListDTO?): ArrayList<Movie> {
        var movie: Movie
        val moviesList = ArrayList<Movie>()
        moviesListDTO?.items?.forEach { item ->
            val dtoMoviesDetails = MoviesRepo.api.getFilmDetails(
                item.kinopoiskId ?: 0
            ).execute().body()
            dtoMoviesDetails?.apply {
                movie = Movie(
                    kinopoiskId,
                    nameRu,
                    nameOriginal,
                    shortDescription,
                    description,
                    year,
                    posterUrl,
                    ratingKinopoisk,
                    webUrl,
                    MovieState.STATE_NULL
                )
                if (movie.description != null) {
                    moviesList.add(movie)
                }
            }
        }
        return if (moviesList.isEmpty()){
            arrayListOf(getErrorMovie())
        } else {
            moviesList
        }
    }

    override fun getMoreMovies(moviesListDTO: MoviesListDTO, from: Int): List<Movie> {

        return finalMoviesList
    }

    override fun saveEntity(movie: Movie) {
        Database.db.myFilmsDao().insert(convertMovieToEntity(movie))
    }

    override fun updateEntity(movie: Movie) {
        Database.db.myFilmsDao().updateEntity(movie.kinopoiskId, movie.state)
    }

    override fun deleteEntity(id: Int) {
        Database.db.myFilmsDao().delete(id)
    }

    override fun deleteAll() {
        Database.db.myFilmsDao().deleteAll()
    }

    override fun getWantedMovies(): List<Movie> {
        return convertFilmEntityToMovie(Database.db.myFilmsDao().wanted())
    }

    override fun getViewedMovies(): List<Movie> {
        return convertFilmEntityToMovie(Database.db.myFilmsDao().viewed())
    }

    override fun getMoviesFromLocalStorage(): List<Movie> {
        return convertFilmEntityToMovie(Database.db.myFilmsDao().all())
    }

    private fun convertMovieToEntity(movie: Movie): MyFilmEntity {
        return MyFilmEntity(0, movie.kinopoiskId, movie.nameRu, movie.nameEn, movie.shortDescription, movie.description, movie.year, movie.posterUrl, movie.rating, movie.webUrl, movie.state)
    }

    private fun convertFilmEntityToMovie(entityList: List<MyFilmEntity>): List<Movie> {
        return entityList.map {
            Movie(it.kinopoiskId, it.nameRu, it.nameEn, it.shortDescription, it.description, it.year, it.posterUrl, it.rating, it.webUrl, it.state)
        }
    }

    override fun getFakeMoviesFromLocalStorage(moviesListDTO: MoviesListDTO?): ArrayList<Movie> {
        var movie: Movie
        finalMoviesList.clear()

        moviesListDTO?.items?.forEach { item ->
            val movieDetailsDTO = getMovieDetailsDTO(
                item.kinopoiskId ?: 0
            )
            with(movieDetailsDTO) {
                movie = Movie(
                    kinopoiskId,
                    nameRu,
                    nameOriginal,
                    shortDescription,
                    description,
                    year,
                    posterUrl,
                    ratingKinopoisk,
                    webUrl,
                    MovieState.STATE_NULL
                )
               if (movie.description != null) {
                   finalMoviesList.add(movie)
               }
            }
        }

        return finalMoviesList

    }

    override fun getTrailer(id: Int): VideoResponseItem? {
        val dtoVideos = MoviesRepo.api.getVideo(id).execute().body()
        dtoVideos?.items?.forEach {
            if (
                (it.name.contains("русский") or
                it.name.contains("Русский") or
                it.name.contains("РУССКИЙ")) and
                (it.site == "YOUTUBE")
            ) {
                return it
            }
        }
        dtoVideos?.items?.forEach {
            if ((it.name.contains("trailer") or
                it.name.contains("Trailer") or
                it.name.contains("TRAILER") or
                it.name.contains("трейлер") or
                it.name.contains("Трейлер") or
                it.name.contains("ТРЕЙЛЕР")) and
                (it.site == "YOUTUBE")
            )
                return it
        }
        return null
//        return dtoVideos?.items?.random()
    }
}