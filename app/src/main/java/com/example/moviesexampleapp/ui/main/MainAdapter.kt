package com.example.moviesexampleapp.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.moviesexampleapp.R
import com.example.moviesexampleapp.databinding.ItemMovieInListBinding
import com.example.moviesexampleapp.model.entities.*

class MainAdapter(private val adapterInterface: MainAdapterInterface) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var moviesList = ArrayList<Movie?>()
    private lateinit var inListBinding: ItemMovieInListBinding


    @SuppressLint("NotifyDataSetChanged")
    fun setMoviesList(data: List<Movie>) {
        moviesList.addAll(data)
        if (moviesList.size < 5){
            moviesList.clear()
            moviesList.add(getErrorMovie())
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        inListBinding =
            ItemMovieInListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(inListBinding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (moviesList[position]?.kinopoiskId == 0) {
            holder.loading()
        } else {
            holder.bind(moviesList[position] ?: getDefaultMovie())
        }
/*        val movie = moviesList[position]
        holder.text.text = movie.description
        holder.button.setOnClickListener{
            adapterInterface.onItemClicked(movie)
        }*/
        if(moviesList.size != 1){
            if (position == moviesList.size - 1) {
                adapterInterface.onDataEnding()
            }
        }
    }

    override fun getItemCount(): Int = moviesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addMovies(requestedMoviesList: List<Movie>) {
        moviesList.removeAt(moviesList.size - 1)
        moviesList.addAll(requestedMoviesList)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(itemView: View) : ViewHolder(itemView) {

        private val text = with(inListBinding){
            itemMovieText
        }

        private val poster = with(inListBinding){
            itemMovieImage
        }

        private val ratingContainer = with(inListBinding){
            ratingCardView
        }

        private val rating = with(inListBinding){
            ratingText
        }

        private val progressBar = with(inListBinding){
            progressBar
        }

        fun bind(movie: Movie){
            progressBar.visibility = View.GONE
            text.text = movie.description
            if (movie.kinopoiskId != -1){
                poster.load(movie.posterUrl)

                val currentRating = movie.rating
                rating.text = currentRating?.toString() ?: "?"

                adapterInterface.setRatingColor(rating)

                itemView.setOnClickListener{
                    adapterInterface.onItemClicked(moviesList[bindingAdapterPosition] ?: getDefaultMovie())
                }
            } else {
                ratingContainer.visibility = View.GONE
            }
        }

        fun loading(){
            progressBar.visibility = View.VISIBLE
            ratingContainer.visibility = View.GONE
            text.text = null
            itemView.setOnClickListener{}
        }

    }


}