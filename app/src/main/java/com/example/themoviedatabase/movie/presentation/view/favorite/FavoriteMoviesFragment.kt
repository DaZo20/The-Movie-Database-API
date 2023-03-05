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
import com.example.themoviedatabase.common.RecyclerListenerFavoriteMovie
import com.example.themoviedatabase.databinding.FragmentFavoriteMoviesBinding
import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.presentation.view.detail.FavoriteDetailFragment
import com.example.themoviedatabase.movie.presentation.viewmodel.MoviesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment(), RecyclerListenerFavoriteMovie {

    @Inject
    lateinit var moviesViewModel: MoviesViewModel
    private var favoriteMovieBinding: FragmentFavoriteMoviesBinding? = null
    private val favDetailFragment: Fragment by lazy { FavoriteDetailFragment.newInstance() }
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
                moviesViewModel.favMovies.collect { favMovies ->
                    if (favMovies != null) {
                        loadFavoriteMovies(data = favMovies)
                    }
                }
            }
        }
    }

    private fun loadFavoriteMovies(data: FavoriteMovieEntity) {
        (favoriteMovieBinding?.rvFavoriteMovies?.adapter as? FavoriteMovieAdapter)?.updateData(
            newData = listOfNotNull(data)
        )
    }

    private fun initViews() {
        with(favoriteMovieBinding?.rvFavoriteMovies) {
            this?.layoutManager = GridLayoutManager(context, 3)
            this?.adapter = FavoriteMovieAdapter(onItemClicked = { favMovie ->
                onRecyclerItemSelected(favMovie)
            })
            moviesViewModel.fetchFavoriteMovies()
        }
    }

    override fun onRecyclerItemSelected(favMovie: FavoriteMovieEntity) {
        val detailFragment: BottomSheetDialogFragment = favDetailFragment as BottomSheetDialogFragment
        val bundle = Bundle()
        bundle.putString("imgFav", favMovie.poster_path)
        bundle.putString("titleFav", favMovie.title)
        bundle.putString("dateFav", favMovie.release_date)
        bundle.putString("overviewFav", favMovie.overview)
        bundle.putString("voteFav", favMovie.vote_average)
        detailFragment.arguments = bundle
        parentFragmentManager.setFragmentResult("favorite_detail_key", bundle)
        detailFragment.show(parentFragmentManager, "favorite_detail")
    }

    companion object {
        fun newInstance(): FavoriteMoviesFragment = FavoriteMoviesFragment().apply { arguments = Bundle() }
    }
}