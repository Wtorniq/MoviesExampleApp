package com.example.moviesexampleapp.model.entities.rest.rest_entities.filters

data class FiltersDTO(
    val countries: List<FiltersResponseCountries>,
    val genres: List<FiltersResponseGenres>
)

