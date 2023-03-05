package com.example.themoviedatabase.movie.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//


@Entity(tableName = "favorite_movies_table", indices = [Index(value = ["title"], unique = true)])
data class FavoriteMovieEntity (
        @PrimaryKey(autoGenerate = true) val id: Int,
        val title: String,
        val poster_path: String?,
        val vote_average: String,
        val release_date: String,
        val overview: String,
        )
