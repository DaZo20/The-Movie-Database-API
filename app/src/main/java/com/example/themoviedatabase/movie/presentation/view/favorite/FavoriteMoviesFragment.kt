package com.example.themoviedatabase.movie.presentation.view.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedatabase.databinding.FragmentFavoriteMoviesBinding
import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.presentation.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

    @Inject
    lateinit var moviesViewModel: MoviesViewModel
    private var favoriteMovieBinding: FragmentFavoriteMoviesBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentFavoriteMoviesBinding.inflate(inflater, container, false).also {
        favoriteMovieBinding = it
        initViews()
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        favoriteMovieBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesViewModel.favMovies.collectLatest { favMovies ->
                    if (favMovies != null) {
                        loadFavoriteMovies(data = favMovies)
                    }
                }
            }
        }
    }

    private fun loadFavoriteMovies(data: FavoriteMovieEntity) {
        (favoriteMovieBinding?.rvFavoriteMovies?.adapter as? FavoriteMovieAdapter)?.updateData(
            newData = listOf(data)
        )
    }

    private fun initViews() {
        with(favoriteMovieBinding?.rvFavoriteMovies) {
            this?.layoutManager = GridLayoutManager(context, 3)
            this?.adapter = FavoriteMovieAdapter()
            moviesViewModel.fetchFavoriteMovies()
        }
    }

    companion object {
        fun newInstance(): FavoriteMoviesFragment = FavoriteMoviesFragment().apply { arguments = Bundle() }
    }
}