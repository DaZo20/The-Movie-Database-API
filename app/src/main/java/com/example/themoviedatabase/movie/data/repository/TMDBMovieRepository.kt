package com.example.themoviedatabase.movie.data.repository

import com.example.themoviedatabase.movie.data.datasource.MovieDataSource
import com.example.themoviedatabase.movie.data.utils.toMovieEntity
import com.example.themoviedatabase.movie.data.utils.toMovies
import com.example.themoviedatabase.movie.domain.MovieDomainLayerContract
import com.example.themoviedatabase.movie.domain.model.Movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Singleton

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

@Singleton
object TMDBMovieRepository :
    MovieDomainLayerContract.DataLayer.MovieRepository {

    private var nextPage: Int = 1

    lateinit var moviesRemoteDataSource: MovieDataSource.Remote
    lateinit var moviesLocalDataSource: MovieDataSource.Local


    override suspend fun getAllPopularMovies(): Result<Movies> =
        moviesRemoteDataSource.getAllPopularMovies().map { dto ->
            dto.toMovies()
        }


    override suspend fun getPopularMoviesNextPage(): Result<Movies> =
        moviesRemoteDataSource.getAllPopularMoviesNextPage(page = nextPage).map { dto ->
            dto.toMovies().also {
            nextPage++
            }
        }


    override suspend fun getPopularMoviesByName(query: String): Result<Movies> =
        withContext(Dispatchers.IO) {
            moviesRemoteDataSource.getSearchedMovies(query).map { dto ->
                dto.toMovies()
            }
        }
}