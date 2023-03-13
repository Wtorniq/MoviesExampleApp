package com.example.moviesexampleapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviesexampleapp.R
import com.example.moviesexampleapp.model.entities.getDefaultMovie
import com.example.moviesexampleapp.ui.details.MovieDetailsFragment
import com.example.moviesexampleapp.ui.main.MainFragment
import com.example.moviesexampleapp.ui.myMovies.MyMoviesFragment
import com.example.moviesexampleapp.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putParcelable(MovieDetailsFragment.ARG_MOVIE, getDefaultMovie())
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MyMoviesFragment.newInstance())
                .commitNow()
        }
    }
}