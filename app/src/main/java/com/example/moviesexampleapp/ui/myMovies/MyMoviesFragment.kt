package com.example.moviesexampleapp.ui.myMovies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.moviesexampleapp.R
import com.example.moviesexampleapp.databinding.FragmentMyMoviesBinding
import com.example.moviesexampleapp.model.AppState
import com.example.moviesexampleapp.model.entities.Movie
import com.example.moviesexampleapp.model.entities.MovieState
import com.example.moviesexampleapp.ui.details.MovieDetailsFragment
import com.example.moviesexampleapp.ui.details.MovieDetailsFragment.Companion.ARG_MOVIE
import com.example.moviesexampleapp.ui.main.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyMoviesFragment : Fragment() {

    private val viewModel: MyMoviesViewModel by viewModel()
    private var wantedAdapter: MyMoviesAdapter? = null
    private var viewedAdapter: MyMoviesAdapter? = null
    private var _binding: FragmentMyMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWantedAdapter()
        initViewedAdapter()
    }

    private fun initViewedAdapter() {
        viewedAdapter = MyMoviesAdapter(object : MyMoviesAdapterInterface{
            override fun onItemClicked(movie: Movie) {
/*                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(movie.webUrl)
                startActivity(intent)*/
                val bundle = Bundle()
                bundle.putParcelable(ARG_MOVIE, movie)
                parentFragmentManager.
                beginTransaction().
                replace(R.id.container, MovieDetailsFragment.newInstance(bundle)).
                addToBackStack(null).
                commit()
            }
            override fun onItemLongClicked(itemView: View, movie: Movie) {
                val popupMenu = PopupMenu(context, itemView)
                popupMenu.menuInflater.inflate(R.menu.menu_popup_viewed, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.action_move_to_wanted -> {
                            movie.state = MovieState.STATE_WANTED
                            viewModel.updateMyMovie(movie)
                            true
                        }
                        R.id.action_delete -> {
                            viewModel.deleteMyMovie(movie.kinopoiskId)
                            true
                        }
                        else -> {false}
                    }
                }
                popupMenu.show()
            }

        })
    }

    private fun initWantedAdapter() {
        wantedAdapter = MyMoviesAdapter(object : MyMoviesAdapterInterface{
            override fun onItemClicked(movie: Movie) {
/*                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(movie.webUrl)
                startActivity(intent)*/
                val bundle = Bundle()
                bundle.putParcelable(ARG_MOVIE, movie)
                parentFragmentManager.
                beginTransaction().
                replace(R.id.container, MovieDetailsFragment.newInstance(bundle)).
                addToBackStack(null).
                commit()
            }

            override fun onItemLongClicked(itemView: View, movie: Movie) {
                val popupMenu = PopupMenu(context, itemView)
                popupMenu.menuInflater.inflate(R.menu.menu_popup_wanted, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.action_move_to_viewed -> {
                            movie.state = MovieState.STATE_VIEWED
                            viewModel.updateMyMovie(movie)
                            true
                        }
                        R.id.action_delete -> {
                            viewModel.deleteMyMovie(movie.kinopoiskId)
                            true
                        }
                        else -> {false}
                    }
                }
                popupMenu.show()
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            bottomNav.setOnItemSelectedListener { item ->
                if (item.itemId == R.id.action_search){
                    val fragmentManager = parentFragmentManager
                    if (fragmentManager.popBackStackImmediate()){
                        fragmentManager.popBackStack()
                    } else {
                        fragmentManager.beginTransaction()
                            .replace(R.id.container, MainFragment.newInstance())
                            .commit()
                    }
                }
                return@setOnItemSelectedListener true
            }
            val lmWanted = LinearLayoutManager(context)
            lmWanted.orientation = LinearLayoutManager.HORIZONTAL
            val lmViewed = LinearLayoutManager(context)
            lmViewed.orientation = LinearLayoutManager.HORIZONTAL

            wantedListRoot.layoutManager = lmWanted
            viewedListRoot.layoutManager = lmViewed

            wantedListRoot.adapter = wantedAdapter
            viewedListRoot.adapter = viewedAdapter

            val observer = Observer<AppState>{renderMyMovies(it)}
            viewModel.getLiveData().observe(viewLifecycleOwner, observer)
            viewModel.getMyMovies()
        }
    }

    private fun renderMyMovies(appState: AppState?) {
        when(appState){
            is AppState.SuccessForFirstRequest -> {
                val wantedMoviesList = ArrayList<Movie?>()
                val viewedMoviesList = ArrayList<Movie?>()
                appState.moviesList?.forEach {
                    if (it.state == MovieState.STATE_WANTED) {
                        wantedMoviesList.add(it)
                    } else {
                        viewedMoviesList.add(it)
                    }
                }
                wantedAdapter?.setMoviesList(wantedMoviesList)
                viewedAdapter?.setMoviesList(viewedMoviesList)
            }
            else ->{}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MyMoviesFragment()
    }
}
