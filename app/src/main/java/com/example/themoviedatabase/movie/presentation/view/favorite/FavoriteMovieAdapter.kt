package com.example.themoviedatabase.movie.presentation.view.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.themoviedatabase.R
import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.domain.model.Movie

//
// Created by DaZo20 on 05/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
class FavoriteMovieAdapter(
    var favoriteMovieList: MutableList<FavoriteMovieEntity> = mutableListOf(),
    private val onItemClicked: (FavoriteMovieEntity) -> Unit
) : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val rootView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_adapter_row,parent,false)
        return FavoriteMovieViewHolder(itemView = rootView)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) =
        holder.bindData(favoriteMovieList[position], callback = onItemClicked)

    override fun getItemCount(): Int = favoriteMovieList.size

    fun updateData(newData: List<FavoriteMovieEntity>) {
        favoriteMovieList.addAll(newData.toMutableList())
        notifyDataSetChanged()
    }

    inner class FavoriteMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgMovie: ImageView by lazy { itemView.findViewById(R.id.img_movie) }
        private val movieTitle: TextView by lazy { itemView.findViewById(R.id.movie_title) }
        private val releaseYear: TextView by lazy { itemView.findViewById(R.id.release_year) }
        private val averageVote: TextView by lazy { itemView.findViewById(R.id.vote_average) }

        fun bindData(movie: FavoriteMovieEntity, callback: (FavoriteMovieEntity) -> Unit) {
            imgMovie.load(movie.poster_path)
            movieTitle.text = movie.title
            averageVote.text = movie.vote_average.toString()
            releaseYear.text = movie.release_date

            itemView.setOnClickListener{ callback(movie) }

        }
    }

}