package com.example.themoviedatabase.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.data.db.FavoriteMoviesDao

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

@Database(entities = [FavoriteMovieEntity::class], version = 2)
@TypeConverters(ReferenceListConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun favoriteMoviesDao(): FavoriteMoviesDao

}