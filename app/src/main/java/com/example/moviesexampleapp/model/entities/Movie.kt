package com.example.moviesexampleapp.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val shortDescription: String?,
    val description: String?,
    val year: Int?,
    val posterUrl: String?,
    val rating: Double?,
    val webUrl: String?,
    var state: MovieState
): Parcelable

fun getDefaultMovie() = Movie(
    0,
    "Дефолтный фильм",
    "Default movie",
    "Маленькое короткое описание",
    "Длиннющее огромное описание, которое не влезет даже на страницу",
    0,
    "https://i.picsum.photos/id/237/200/300.jpg?hmac=TmmQSbShHz9CdQm0NkEjx1Dyh_Y984R9LpNrpvH2D_U",
    0.0,
    "https://www.kinopoisk.ru/film/301/",
    MovieState.STATE_NULL
)

fun getErrorMovie() = Movie(
    -1,
    null,
    null,
    null,
    "Произошла ошибка, пожалуйста, обновите страницу",
    0,
    null,
    0.0,
    "https://www.kinopoisk.ru/film/301/",
    MovieState.STATE_NULL
)

fun getEndOfList() = Movie(
    -1,
    null,
    null,
    null,
    "Фильмы по заданным параметрам закончились, укажите другие или выберете уже что-то",
    0,
    null,
    0.0,
    "https://www.kinopoisk.ru/film/301/",
    MovieState.STATE_NULL

)

fun getMoviesList() = listOf(
    Movie(
        123,
        "Типа фильм",
        "Such a film",
        "Маленькое короткое описание",
        "Длиннющее огромное описание, которое не влезет даже на страницу",
        2020,
        "https://kinopoiskapiunofficial.tech/images/posters/kp/301.jpg",
        5.5,
        "https://www.kinopoisk.ru/film/301/",
        MovieState.STATE_NULL

    ),
    Movie(
        123,
        "Типа фильм",
        "Such a film",
        "Маленькое короткое описание",
        "Длиннющее огромное описание, которое не влезет даже на страницу",
        2020,
        "https://kinopoiskapiunofficial.tech/images/posters/kp/301.jpg",
        5.5,
        "https://www.kinopoisk.ru/film/301/",
        MovieState.STATE_NULL

    ),
    Movie(
        123,
        "Типа фильм",
        "Such a film",
        "Маленькое короткое описание",
        "Длиннющее огромное описание, которое не влезет даже на страницу",
        2020,
        "https://kinopoiskapiunofficial.tech/images/posters/kp/301.jpg",
        5.5,
        "https://www.kinopoisk.ru/film/301/",
        MovieState.STATE_NULL

    ),
    Movie(
        123,
        "Типа фильм",
        "Such a film",
        "Маленькое короткое описание",
        "Длиннющее огромное описание, которое не влезет даже на страницу",
        2020,
        "https://kinopoiskapiunofficial.tech/images/posters/kp/301.jpg",
        5.5,
        "https://www.kinopoisk.ru/film/301/",
        MovieState.STATE_NULL

    )
)

fun getSameMoviesList() = listOf(
    Movie(
        123,
        "Похожий фильм",
        "Same film",
        "Маленькое короткое описание похожего фильма",
        "Длиннющее огромное описание похожего фильма, которое не влезет даже на страницу",
        2020,
        "https://kinopoiskapiunofficial.tech/images/posters/kp/301.jpg",
        5.5,
        "https://www.kinopoisk.ru/film/301/",
        MovieState.STATE_NULL
    ),
    Movie(
        123,
        "Похожий фильм",
        "Same film",
        "Маленькое короткое описание похожего фильма",
        "Длиннющее огромное описание похожего фильма, которое не влезет даже на страницу",
        2020,
        "https://kinopoiskapiunofficial.tech/images/posters/kp/301.jpg",
        5.5,
        "https://www.kinopoisk.ru/film/301/",
        MovieState.STATE_NULL
    ),
    Movie(
        123,
        "Похожий фильм",
        "Same film",
        "Маленькое короткое описание похожего фильма",
        "Длиннющее огромное описание похожего фильма, которое не влезет даже на страницу",
        2020,
        "https://kinopoiskapiunofficial.tech/images/posters/kp/301.jpg",
        5.5,
        "https://www.kinopoisk.ru/film/301/",
        MovieState.STATE_NULL
    ),
    Movie(
        123,
        "Похожий фильм",
        "Same film",
        "Маленькое короткое описание похожего фильма",
        "Длиннющее огромное описание похожего фильма, которое не влезет даже на страницу",
        2020,
        "https://kinopoiskapiunofficial.tech/images/posters/kp/301.jpg",
        5.5,
        "https://www.kinopoisk.ru/film/301/",
        MovieState.STATE_NULL
    )
)