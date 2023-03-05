package com.example.themoviedatabase.movie.data.db

import androidx.room.*

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
@Dao
interface FavoriteMoviesDao {

    @Query("SELECT * FROM favorite_movies_table")
    suspend fun getAllFavoriteMovies(): List<FavoriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteMovie(favMovie: FavoriteMovieEntity)

    @Delete
    suspend fun deleteFavoriteMovie(favMovie: FavoriteMovieEntity)
}