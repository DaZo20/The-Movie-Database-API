package com.example.themoviedatabase.movie.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.themoviedatabase.BuildConfig
import com.example.themoviedatabase.R
import com.example.themoviedatabase.movie.domain.model.Movie

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
private const val API_IMAGES_URL = BuildConfig.API_IMAGES_URL
class MovieAdapter(
     var movieList: MutableList<Movie> = mutableListOf()
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(R.layout.movie_adapter_row ,parent,false)
        return MovieViewHolder(itemView = rootView)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) =
        holder.bindData(movieList[position])

    override fun getItemCount(): Int = movieList.size

    fun updateData(newData: List<Movie>) {
        movieList.addAll(newData.toMutableList())
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgMovie: ImageView by lazy { itemView.findViewById(R.id.img_movie) }
        private val movieTitle: TextView by lazy { itemView.findViewById(R.id.movie_title) }
        private val releaseYear: TextView by lazy { itemView.findViewById(R.id.release_year) }
        private val averageVote: TextView by lazy { itemView.findViewById(R.id.vote_average) }

        fun bindData(movie: Movie) {
            imgMovie.load(API_IMAGES_URL + movie.poster_path)
            movieTitle.text = movie.title
            averageVote.text = movie.vote_average.toString()
            releaseYear.text = movie.release_date
        }

    }

}