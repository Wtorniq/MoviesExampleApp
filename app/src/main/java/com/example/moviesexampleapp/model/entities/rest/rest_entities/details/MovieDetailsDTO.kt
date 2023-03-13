package com.example.moviesexampleapp.model.entities.rest.rest_entities.details

import com.example.moviesexampleapp.model.entities.rest.rest_entities.main_list.MoviesListDTO

data class MovieDetailsDTO(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameOriginal: String?,
    val slogan: String?,
    val countries: List<CountryDTO>?,
    val genres: List<GenreDTO>?,
    val shortDescription: String?,
    val description: String?,
    val posterUrl: String?,
    val ratingKinopoisk: Double?,
    val year: Int?,
    val webUrl: String?
)

fun getMovieDetailsDTO(id: Int): MovieDetailsDTO {
    val name: String
    var rating: Double? = 0.0
    when (id) {
        100 -> {name = "a"
        rating = 0.3}
        101 -> {name = "b"
        rating = 2.3}
        102 -> {name = "c"
        rating = 4.5}
        103 -> {name = "d"
        rating = 7.0}
        104 -> {name = "e"
        rating = 9.1}
        105 -> {name = "f"}
        106 -> {name = "g"}
        107 -> {name = "h"}
        108 -> {name = "i"}
        109 -> {name = "j"}
        110 -> {name = "k"}
        else -> {name = ""}
    }
    return MovieDetailsDTO(
        id,
        "name",
        "",
        "",
        listOf(),
        listOf(),
        "",
        name,
        "",
        rating?: 0.0,
        0,
        "https://www.kinopoisk.ru/film/301/"
    )
}
