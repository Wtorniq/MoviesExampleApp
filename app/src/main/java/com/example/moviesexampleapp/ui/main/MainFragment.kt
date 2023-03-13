package com.example.moviesexampleapp.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.moviesexampleapp.R
import com.example.moviesexampleapp.databinding.FragmentMainBinding
import com.example.moviesexampleapp.model.AppState
import com.example.moviesexampleapp.model.entities.Movie
import com.example.moviesexampleapp.model.entities.Settings
import com.example.moviesexampleapp.model.entities.getDefaultSetting
import com.example.moviesexampleapp.model.entities.getMoviesList
import com.example.moviesexampleapp.ui.details.MovieDetailsFragment
import com.example.moviesexampleapp.ui.myMovies.MyMoviesFragment
import com.example.moviesexampleapp.ui.settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private var adapter: MainAdapter? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var settings: Settings = getDefaultSetting()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        adapter = MainAdapter(object : MainAdapterInterface {
            override fun onItemClicked(movie: Movie) {
                val fragmentManager = activity?.supportFragmentManager
                fragmentManager?.let {
                    val bundle = Bundle().apply {
                        putParcelable(MovieDetailsFragment.ARG_MOVIE, movie)
                    }
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, MovieDetailsFragment.newInstance(bundle))
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            }

            override fun onDataEnding() {
                viewModel.getMoreMovies()
            }

            override fun setRatingColor(view: TextView) {
                if (view.text != "?"){
                    when(view.text.toString().toDouble()){
                        in 0.0..2.0 ->{
                            view.setTextColor(resources.getColor(R.color.red))
                        }
                        in 2.1..4.0 ->{
                            view.setTextColor(resources.getColor(R.color.orange))
                        }
                        in 4.1..6.0 ->{
                            view.setTextColor(resources.getColor(R.color.yellow))
                        }
                        in 6.1..8.0 ->{
                            view.setTextColor(resources.getColor(R.color.semiGreen))
                        }
                        in 8.1..10.0 ->{
                            view.setTextColor(resources.getColor(R.color.green))
                        }
                    }
                }
            }
        })
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            toolbar.setOnMenuItemClickListener {
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
                return@setOnMenuItemClickListener true
            }

            bottomNav.setOnItemSelectedListener { item ->
                if (item.itemId == R.id.action_my_movies){
                    val fragmentManager = parentFragmentManager
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, MyMoviesFragment.newInstance())
                        .addToBackStack("mainBackstack")
                        .commit()
                }
                return@setOnItemSelectedListener true
            }
            val lm = LinearLayoutManager(requireContext())
            lm.orientation = LinearLayoutManager.VERTICAL
            mainListRoot.layoutManager = lm
            PagerSnapHelper().attachToRecyclerView(mainListRoot)

            getSettings()
            val observer = Observer<AppState>{renderMovies(it)}
            viewModel.getLiveData().observe(viewLifecycleOwner, observer)

            with (settings) {
                viewModel.getMovie(type, genre, country, yearFrom, yearTo, ratingFrom, ratingTo)
            }
            mainListRoot.adapter = adapter
            adapter?.stateRestorationPolicy = Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        }
    }

    private fun renderMovies(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.SuccessForFirstRequest -> {
                progress.visibility = View.GONE
                val moviesList = appState.moviesList ?: getMoviesList()
                adapter?.setMoviesList(moviesList)
/*                adapter = MainAdapter(object: MainAdapterInterface {
                    override fun onItemClicked(movie: Movie) {
                        val fragmentManager = activity?.supportFragmentManager
                        fragmentManager?.let {
                            val bundle = Bundle().apply {
                                putParcelable(MovieDetailsFragment.ARG_MOVIE, movie)
                            }
                        fragmentManager.beginTransaction()
                            .replace(R.id.container, MovieDetailsFragment.newInstance(bundle))
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                        }
                    }

                    override fun onDataEnding() {
//                        viewModel.getMoreMovies(from)
                    }
                }).apply {
                    setMoviesList(moviesList)
                }*/
            }
            is AppState.SuccessForUpdateList -> {
                val moviesList = appState.moviesList
                adapter?.addMovies(moviesList)
            }
            is AppState.Error -> {

            }
            is AppState.Loading -> {
                progress.visibility = View.VISIBLE
            }
        }

    }

    private fun getSettings() {
        activity?.let {
            settings.type = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getString(argType, getDefaultSetting().type)?: getDefaultSetting().type
            settings.country = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getString(argCountry, getDefaultSetting().country)?: getDefaultSetting().country
            settings.genre = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getString(argGenre, getDefaultSetting().genre)?: getDefaultSetting().genre
            settings.yearFrom = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getInt(argYearFrom, getDefaultSetting().yearFrom)?: getDefaultSetting().yearFrom
            settings.yearTo = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getInt(argYearTo, getDefaultSetting().yearTo)?: getDefaultSetting().yearTo
            settings.ratingFrom = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getInt(argRatingFrom, getDefaultSetting().ratingFrom)?: getDefaultSetting().ratingFrom
            settings.ratingTo = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getInt(argRatingTo, getDefaultSetting().ratingTo)?: getDefaultSetting().ratingTo
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        fun newInstance() = MainFragment()
    }
}
