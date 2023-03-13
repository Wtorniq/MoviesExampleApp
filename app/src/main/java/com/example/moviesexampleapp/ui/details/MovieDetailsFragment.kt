package com.example.moviesexampleapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import coil.load
import com.example.moviesexampleapp.App
import com.example.moviesexampleapp.R
import com.example.moviesexampleapp.databinding.FragmentDetailsMovieBinding
import com.example.moviesexampleapp.model.entities.Movie
import com.example.moviesexampleapp.model.entities.MovieState
import com.example.moviesexampleapp.model.entities.rest.rest_entities.video.VideoResponseItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import com.google.android.youtube.player.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment(){

    private val viewModel: MovieDetailsViewModel by viewModel()
    private var _binding: FragmentDetailsMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>(ARG_MOVIE)
//        val movie = arguments?.getParcelable(ARG_MOVIE, Movie::class.java)
        val observer = Observer<String>{setVisibilityToButton(it)}
        viewModel.getIsAddedLiveData().observe(viewLifecycleOwner, observer)
        viewModel.checkIsAddedToMyMovies(movie)
        with(binding){
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_share -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(movie?.webUrl)
                        startActivity(intent)
                        return@setOnMenuItemClickListener true
                    }
                    else -> {return@setOnMenuItemClickListener false}
                }
            }
            name.text = movie?.nameRu ?: movie?.nameEn
            poster.load(movie?.posterUrl)
            description.text = movie?.description
            ratingText.text = movie?.rating?.toString() ?: "?"
            val trailerObserver = Observer<VideoResponseItem>{showTrailer(it)}
            viewModel.getTrailerLiveData().observe(viewLifecycleOwner, trailerObserver)

            trailerButton.setOnClickListener {
                viewModel.getTrailer(movie?.kinopoiskId ?: 0)
            }
            actionAddToFavourite.setOnClickListener {
                if(movie != null){
                    movie.state = MovieState.STATE_WANTED
                    viewModel.saveMyMovie(movie)
                }
                actionAddToFavourite.visibility = View.GONE
            }
//            showTrailer("http://www.filmz.ru/videos/files/download/21059/hd1080/?url=http%3A%2F%2Fvideos.hd-trailers.net%2Fapocalypse-now-bluray-trailer-1080p-HDTN.mp4")
//            showTrailer("https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1")
//            player.loadUrl("https://youtu.be/a4NT5iBFuZs")
        }

    }

    private fun setVisibilityToButton(buttonState: String?) = with(binding) {
        when(buttonState) {
            "added" -> {
                actionAddToFavourite.visibility = View.GONE
            }
            else -> {
                actionAddToFavourite.visibility = View.VISIBLE
            }
        }
    }

    private fun showTrailer(trailer: VideoResponseItem?) = with(binding) {
//        val intent =
//            Intent(activity, TrailerActivity::class.java)
//        YouTubeIntents.createPlayVideoIntent(App.appInstance, "dQw4w9WgXcQ")
//        startActivity(intent)
//        videoView.setVideoPath(trailerUrl)
//        val uri = Uri.parse(trailerUrl)
        if (trailer?.url != null) {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(trailer.url)
            startActivity(intent)

        } else {
            make(root, resources.getString(R.string.no_trailers), Snackbar.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_MOVIE = "ARG_MOVIE"
        fun newInstance(bundle: Bundle): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}