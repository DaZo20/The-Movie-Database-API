package com.example.themoviedatabase.movie.presentation.view.popular

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
import com.example.themoviedatabase.BuildConfig.API_IMAGES_URL
import com.example.themoviedatabase.common.RecyclerListenerMovie
import com.example.themoviedatabase.databinding.FragmentMovieBinding
import com.example.themoviedatabase.movie.domain.model.Movie
import com.example.themoviedatabase.movie.domain.model.Movies
import com.example.themoviedatabase.movie.presentation.view.detail.DetailFragment
import com.example.themoviedatabase.movie.presentation.viewmodel.MoviesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MovieFragment : Fragment(), RecyclerListenerMovie {

    @Inject
    lateinit var moviesViewModel: MoviesViewModel
    private var movieFragmentBinding: FragmentMovieBinding? = null
    private val detailFragment: Fragment by lazy { DetailFragment.newInstance() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMovieBinding.inflate(inflater, container, false).also {
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
            this?.adapter = MovieAdapter(onItemClicked = { movie ->
                onRecyclerItemSelected(movie)
            })
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
                        val adapter = movieFragmentBinding?.rvMoviesData?.adapter as MovieAdapter
                        if (adapter.movieList.isNotEmpty()) {
                            moviesViewModel.onEndOfScrollReached()
                        }
                    }
                }
            })
        }
    }

    private fun getMoviesByTitle() {
        val adapter = movieFragmentBinding?.rvMoviesData?.adapter as MovieAdapter

        movieFragmentBinding?.searchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                moviesViewModel.fetchMoviesByTitle(query.toString().uppercase())
                movieFragmentBinding?.searchView?.setQuery("", false)
                movieFragmentBinding?.searchView?.clearFocus()
                adapter.movieList.clear()
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

    override fun onRecyclerItemSelected(movie: Movie) {
        val detailFragment: BottomSheetDialogFragment = detailFragment as BottomSheetDialogFragment
        val bundle = Bundle()
        bundle.putString("img", API_IMAGES_URL + movie.poster_path)
        bundle.putString("title", movie.title)
        bundle.putString("date", movie.release_date)
        bundle.putString("overview", movie.overview)
        bundle.putString("vote", movie.vote_average.toString())
        detailFragment.arguments = bundle
        parentFragmentManager.setFragmentResult("detail_key", bundle)
        detailFragment.show(parentFragmentManager, "detail")
    }

}