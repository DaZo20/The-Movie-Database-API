package com.example.themoviedatabase.movie.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

@Entity(tableName = "favorite_movies_table")
data class FavoriteMovieEntity (
        val adult: Boolean,
        val page: Int,
        val backdrop_path: String?,
        val genre_ids: List<Int>,
        @PrimaryKey(autoGenerate = true) val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String?,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
        )
