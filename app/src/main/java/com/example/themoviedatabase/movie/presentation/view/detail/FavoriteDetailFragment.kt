package com.example.themoviedatabase.movie.presentation.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import com.example.themoviedatabase.R
import com.example.themoviedatabase.databinding.FragmentFavoriteDetailBinding
import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.presentation.viewmodel.MoviesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteDetailFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var moviesViewModel: MoviesViewModel
    private var favoriteDetailBinding: FragmentFavoriteDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoriteDetailBinding.inflate(inflater,container, false).also {
        favoriteDetailBinding = it
        obtainBundleDataAndFavDeleteButton()
        favoriteDetailBinding?.closeButton?.setOnClickListener { dismiss() }

    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        favoriteDetailBinding = null
    }

    private fun obtainBundleDataAndFavDeleteButton() {
        parentFragmentManager.setFragmentResultListener("favorite_detail_key", this) { requestKey, result ->
            val img = result.getString("imgFav")
            val title = result.getString("titleFav")
            val date = result.getString("dateFav")
            val overview = result.getString("overviewFav")
            val vote = result.getString("voteFav")

            favoriteDetailBinding?.posterImg?.load(img)
            favoriteDetailBinding?.titleTv?.text = title
            favoriteDetailBinding?.dateTv?.text = date
            favoriteDetailBinding?.desctriptionTv?.text = overview
            favoriteDetailBinding?.voteAverage?.text = vote

            favoriteDetailBinding?.favDeleteButton?.setOnClickListener {
                moviesViewModel.deleteFavoriteMovie(title.toString())
                dismiss()
                Toast.makeText(context,"${title.toString()} eliminada de favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance(): FavoriteDetailFragment = FavoriteDetailFragment().apply { arguments = Bundle() }
    }
}