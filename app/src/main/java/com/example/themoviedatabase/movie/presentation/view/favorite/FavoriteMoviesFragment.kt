package com.example.themoviedatabase.movie.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.themoviedatabase.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_movies, container, false)
    }

    companion object {
        fun newInstance(): FavoriteMoviesFragment = FavoriteMoviesFragment().apply { arguments = Bundle() }
    }
}