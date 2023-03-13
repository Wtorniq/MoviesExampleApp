package com.example.moviesexampleapp

import com.example.moviesexampleapp.model.repository.Repository
import com.example.moviesexampleapp.model.repository.RepositoryImpl
import com.example.moviesexampleapp.ui.details.MovieDetailsViewModel
import com.example.moviesexampleapp.ui.main.MainViewModel
import com.example.moviesexampleapp.ui.myMovies.MyMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Repository> { RepositoryImpl() }

    viewModel {
        MainViewModel(get())
    }

    viewModel {
        MovieDetailsViewModel(get())
    }

    viewModel {
        MyMoviesViewModel(get())
    }

}

