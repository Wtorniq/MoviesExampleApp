package com.example.moviesexampleapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesexampleapp.model.entities.Movie
import com.example.moviesexampleapp.model.entities.rest.rest_entities.video.VideoResponseItem
import com.example.moviesexampleapp.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: Repository) : ViewModel() {

    private val isAddedLiveData = MutableLiveData<String>()
    private val trailerLiveData = MutableLiveData<VideoResponseItem>()

    fun getIsAddedLiveData(): LiveData<String> = isAddedLiveData
    fun getTrailerLiveData(): LiveData<VideoResponseItem> = trailerLiveData

    fun getTrailer(id: Int) = getTrailerFromService(id)

    private fun getTrailerFromService(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            trailerLiveData.postValue(repository.getTrailer(id))
        }
    }

    fun saveMyMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveEntity(movie)
        }
    }

    fun checkIsAddedToMyMovies(movie: Movie?) {
        viewModelScope.launch(Dispatchers.IO) {
            var isAdded = false
            repository.getWantedMovies().forEach {
                if (movie?.kinopoiskId == it.kinopoiskId) {
                    isAdded = true
                }
            }
            if (isAdded){
                isAddedLiveData.postValue("added")
            } else {
                isAddedLiveData.postValue("null")
            }
        }
    }
}