package com.example.themoviedatabase.movie.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedatabase.databinding.FragmentMovieBinding
import com.example.themoviedatabase.movie.domain.model.Movies
import com.example.themoviedatabase.movie.presentation.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment() {

    @Inject
    lateinit var moviesViewModel: MoviesViewModel
    private var movieFragmentBinding: FragmentMovieBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMovieBinding.inflate(inflater,container,false).also {
        movieFragmentBinding = it
        initViews()
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        movieFragmentBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesViewModel.movies.collect { movies ->
                    if (movies != null) {
                        loadMovies(data = movies)
                    }
                }
            }
        }
    }

    private fun loadMovies(data: Movies) {
        (movieFragmentBinding?.rvMoviesData?.adapter as? MovieAdapter)?.updateData(
            newData = data.results
        )
    }

    private fun initViews() {
        with(movieFragmentBinding?.rvMoviesData) {
            this?.layoutManager = GridLayoutManager(context, 3)
            this?.adapter = MovieAdapter()
            getMoviesByTitle()
            scrollListener()
        }
    }

    private fun scrollListener() {
        with(movieFragmentBinding?.rvMoviesData) {
            this?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager: LinearLayoutManager? =
                        recyclerView.layoutManager as LinearLayoutManager?
                    if (layoutManager?.findLastCompletelyVisibleItemPosition() == recyclerView.adapter?.itemCount?.minus(
                            1
                        )
                    ) {
                        moviesViewModel.onEndOfScrollReached()
                    }
                }
            })
        }
    }

    private fun getMoviesByTitle() {
        movieFragmentBinding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val adapter = movieFragmentBinding?.rvMoviesData?.adapter as MovieAdapter
                adapter.movieList.clear()
                moviesViewModel.fetchMoviesByTitle(query.toString().uppercase())
                movieFragmentBinding?.searchView?.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    companion object {
        fun newInstance(): MovieFragment = MovieFragment().apply { arguments = Bundle() }
    }

}