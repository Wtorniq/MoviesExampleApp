package com.example.moviesexampleapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesexampleapp.model.AppState
import com.example.moviesexampleapp.model.entities.*
import com.example.moviesexampleapp.model.entities.rest.rest_entities.main_list.MovieDTO
import com.example.moviesexampleapp.model.entities.rest.rest_entities.main_list.MoviesListDTO
import com.example.moviesexampleapp.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: Repository) : ViewModel() {
    private var moviesList: ArrayList<Movie>? = null
    private var currentSettings: Settings? = null
    private var page = 1
    private val liveData = MutableLiveData<AppState>()

    private val fakeDtoMoviesList = MoviesListDTO(buildList {
        for (i in 100..105) {
            add(MovieDTO(i))
        }
    })

    fun getLiveData(): LiveData<AppState> = liveData

    fun getMovie(type: String, genre: String, country: String, yearFrom: Int, yearTo: Int, ratingFrom: Int, ratingTo: Int) = getMovieFromServer(type, genre, country, yearFrom, yearTo, ratingFrom, ratingTo)

    fun getMoreMovies() = getMoreMoviesFromServer()

    private fun getMoreMoviesFromServer() = with(viewModelScope) {
        launch(Dispatchers.IO) {
            page += 1
            val data = repository.getMoviesFromServer(currentSettings?.country!!, currentSettings?.genre!!, 0, 10, 1000, 3000, currentSettings?.type!!, page)
//            val data = repository.getFakeMoviesFromLocalStorage(fakeDtoMoviesList)
            val mutData: MutableList<Movie>?
            if(data != null){
                mutData = data.toMutableList()
                if (page > 5) {
                    mutData.clear()
                    mutData.add(getEndOfList())
                }
                mutData.toList()
                moviesList?.removeAt(moviesList!!.size - 1)
                moviesList?.addAll(mutData)
                withContext(Dispatchers.Main) {
                    liveData.value = AppState.SuccessForUpdateList(mutData)
                }
            }
        }
    }


    private fun getMovieFromServer(type: String, genre: String, country: String, yearFrom: Int, yearTo: Int, ratingFrom: Int, ratingTo: Int) = with(viewModelScope) {
        liveData.value = AppState.Loading
        launch(Dispatchers.IO) {
//            val data = repository.getMoviesFromServer("США", "комедия", 0, 10, 1000, 3000, "FILM")
            getMoviesFromServerWithSettings(type, genre, country, yearFrom, yearTo, ratingFrom, ratingTo)
//           dtoMoviesList = repository.getMoviesFromServer(country, genre, 0, 10, 1000, 3000, type)
//            val data = repository.getMoviesList(dtoMoviesList)
            withContext(Dispatchers.Main) { liveData.value = AppState.SuccessForFirstRequest(moviesList ?: getMoviesList()) }
        }
/*//        Thread{
            dtoMoviesList = MoviesListDTO(buildList {
                for (i in 100..120) {
                    add(MovieDTO(i))
                }
            })
            val data = repository.getMoviesFromLocalStorage(0, dtoMoviesList)
//            val dtoMoviesList = repository.getMoviesFromServer(country, genre, 0, 10, 1000, 3000, type)
//            val data = repository.getMoviesList(dtoMoviesList)
            liveData.postValue(AppState.SuccessForFirstRequest(data ?: getMoviesList()))
//        }.start()*/
    }

    private fun getMoviesFromServerWithSettings(type: String, genre: String, country: String, yearFrom: Int, yearTo: Int, ratingFrom: Int, ratingTo: Int) {
        if (!
            (currentSettings?.type == type
            && currentSettings?.genre == genre
            && currentSettings?.country == country
            && currentSettings?.yearFrom == yearFrom
            && currentSettings?.yearTo == yearTo
            && currentSettings?.ratingFrom == ratingFrom
            && currentSettings?.ratingTo == ratingTo)
        ) {
            page = 1

//            moviesList = repository.getFakeMoviesFromLocalStorage(fakeDtoMoviesList)
            moviesList = repository.getMoviesFromServer(country, genre, ratingFrom, ratingTo, yearFrom, yearTo, type, page)
            moviesList?.add(getDefaultMovie())
            currentSettings = Settings(type, country, genre, ratingFrom, ratingTo, yearFrom, yearTo)
        } else {
            currentSettings = Settings(type, country, genre, ratingFrom, ratingTo, yearFrom, yearTo)
        }
    }
}