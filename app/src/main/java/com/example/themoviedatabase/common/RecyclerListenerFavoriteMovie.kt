package com.example.themoviedatabase.common

import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.domain.model.Movie

//
// Created by DaZo20 on 05/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
interface RecyclerListenerFavoriteMovie {
    fun onRecyclerItemSelected(favMovie: FavoriteMovieEntity)
}