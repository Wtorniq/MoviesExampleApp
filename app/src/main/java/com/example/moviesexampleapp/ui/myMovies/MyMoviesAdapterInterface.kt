package com.example.moviesexampleapp.ui.myMovies

import android.view.View
import androidx.appcompat.view.menu.MenuView.ItemView
import com.example.moviesexampleapp.model.entities.Movie

interface MyMoviesAdapterInterface {
    fun onItemClicked(movie: Movie)
    fun onItemLongClicked(itemView: View, movie: Movie)
}
