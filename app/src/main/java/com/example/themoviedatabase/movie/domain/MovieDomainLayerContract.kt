package com.example.themoviedatabase.movie.domain

import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.domain.model.Movies

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
interface MovieDomainLayerContract {

    interface PresentationLayer {
        interface UseCase<T> {
            suspend operator fun invoke(): Result<T>
        }
    }

    interface DataLayer {
        interface MovieRepository{
            suspend fun getAllPopularMovies(): Result<Movies>
            suspend fun getPopularMoviesNextPage(): Result<Movies>
            suspend fun getPopularMoviesByName(query: String): Result<Movies>
            suspend fun getFavoriteMovies(): List<FavoriteMovieEntity>
            suspend fun insertFavoriteMovies(favMovie: FavoriteMovieEntity)
            suspend fun deleteFavoriteMovies(title: String)
        }
    }

}