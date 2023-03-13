package com.example.moviesexampleapp.ui.myMovies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.moviesexampleapp.databinding.ItemMyMoviesListBinding
import com.example.moviesexampleapp.model.entities.Movie
import com.example.moviesexampleapp.model.entities.getDefaultMovie

class MyMoviesAdapter(private val adapterInterface: MyMoviesAdapterInterface) : Adapter<MyMoviesAdapter.MyMoviesViewHolder>(){

    private var moviesList = ArrayList<Movie?>()
    private lateinit var binding: ItemMyMoviesListBinding


    @SuppressLint("NotifyDataSetChanged")
    fun setMoviesList(data: ArrayList<Movie?>) {
        moviesList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMoviesViewHolder {
        binding = ItemMyMoviesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyMoviesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyMoviesViewHolder, position: Int) {
        holder.bind(moviesList[position] ?: getDefaultMovie())
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner class MyMoviesViewHolder(itemView: View): ViewHolder(itemView){
        private val poster = with(binding){
            itemMovieImage
        }
        private val name = with(binding){
            itemMovieName
        }

        fun bind(movie: Movie){
            poster.load(movie.posterUrl)
            name.text = movie.nameRu ?: movie.nameEn

            itemView.setOnClickListener {
                adapterInterface.onItemClicked(movie)
            }
            itemView.setOnLongClickListener {
                adapterInterface.onItemLongClicked(itemView, movie)
                true
            }
        }
    }
}