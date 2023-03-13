package com.example.moviesexampleapp.ui.myMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesexampleapp.model.AppState
import com.example.moviesexampleapp.model.entities.Movie
import com.example.moviesexampleapp.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyMoviesViewModel(private val repository: Repository) : ViewModel() {

    private val myMoviesLiveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = myMoviesLiveData

    fun getMyMovies() {
        myMoviesLiveData.value = AppState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getMoviesFromLocalStorage()
            myMoviesLiveData.postValue(AppState.SuccessForFirstRequest(data))
        }
    }

    fun updateMyMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateEntity(movie)
            myMoviesLiveData.postValue(AppState.SuccessForFirstRequest(repository.getMoviesFromLocalStorage()))
        }
    }

    fun deleteMyMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEntity(id)
            myMoviesLiveData.postValue(AppState.SuccessForFirstRequest(repository.getMoviesFromLocalStorage()))
        }
    }

}