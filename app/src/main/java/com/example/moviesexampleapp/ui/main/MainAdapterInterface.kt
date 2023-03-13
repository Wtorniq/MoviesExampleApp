package com.example.moviesexampleapp.ui.main

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.moviesexampleapp.model.entities.Movie

interface MainAdapterInterface{
    fun onItemClicked(movie: Movie)
    fun onDataEnding()
    fun setRatingColor(view: TextView)
}