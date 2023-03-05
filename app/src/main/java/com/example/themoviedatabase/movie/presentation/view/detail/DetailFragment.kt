package com.example.themoviedatabase.movie.presentation.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import com.example.themoviedatabase.databinding.FragmentDetailBinding
import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.presentation.viewmodel.MoviesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var moviesViewModel: MoviesViewModel
    private var detailBinding: FragmentDetailBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailBinding.inflate(inflater, container, false).also {
        detailBinding = it
        obtainBundleDataAndFavButton()
        closeDetailFragment()
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        detailBinding = null
    }

    private fun closeDetailFragment() =
        detailBinding?.closeButton?.setOnClickListener { dismiss() }


    private fun obtainBundleDataAndFavButton() {
        parentFragmentManager.setFragmentResultListener("detail_key", this) { requestKey, result ->
            val img = result.getString("img")
            val title = result.getString("title")
            val date = result.getString("date")
            val overview = result.getString("overview")
            val vote = result.getString("vote")

            detailBinding?.posterImg?.load(img)
            detailBinding?.titleTv?.text = title
            detailBinding?.dateTv?.text = date
            detailBinding?.desctriptionTv?.text = overview
            detailBinding?.voteAverage?.text = vote

            detailBinding?.favButton?.setOnClickListener {
                moviesViewModel.saveFavoriteMovies(
                    movie = FavoriteMovieEntity(
                        id = id,
                        title = title.toString(),
                        vote_average = vote.toString(),
                        overview = overview.toString(),
                        poster_path = img.toString(),
                        release_date = date.toString()
                    )
                )
                dismiss()
                Toast.makeText(context,"${title.toString()} añadida a favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        fun newInstance(): DetailFragment = DetailFragment().apply { arguments = Bundle() }
    }

}