package com.example.themoviedatabase.movie.data.datasource

import com.example.themoviedatabase.common.db.ApplicationDatabase
import com.example.themoviedatabase.movie.data.api.MovieService
import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.data.model.MoviesDto
import retrofit2.Retrofit
import javax.inject.Inject

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

interface MovieDataSource {
    interface Remote {
        suspend fun getAllPopularMovies(): Result<MoviesDto?>
        suspend fun getSearchedMovies(query: String): Result<MoviesDto?>
        suspend fun getAllPopularMoviesNextPage(page: Int): Result<MoviesDto?>
        suspend fun getSearchedMoviesNextPage(query: String, page: Int): Result<MoviesDto?>
    }

    interface Local {
        suspend fun saveFavoriteMovie(favMovie: FavoriteMovieEntity)
        suspend fun fetchAllFavoriteMovies(): List<FavoriteMovieEntity>
        suspend fun deleteFavoriteMovie(title: String)
    }
}

class TMDBMovieDataSource @Inject constructor(
    private val retrofitInstance: Retrofit,
    private val roomDatabaseInstance: ApplicationDatabase
) : MovieDataSource.Remote, MovieDataSource.Local {
    override suspend fun getAllPopularMovies(): Result<MoviesDto?> =
        retrofitInstance.create(MovieService::class.java).getAllPopularMovies()
            .runCatching { body() }

    override suspend fun getSearchedMovies(query: String): Result<MoviesDto?> =
        retrofitInstance.create(MovieService::class.java).getSearchedMovies(query = query)
            .runCatching { body() }

    override suspend fun getAllPopularMoviesNextPage(page: Int): Result<MoviesDto?> =
        retrofitInstance.create(MovieService::class.java).getAllPopularMovies(page = page)
            .runCatching { body() }

    override suspend fun getSearchedMoviesNextPage(query: String, page: Int): Result<MoviesDto?> =
        retrofitInstance.create(MovieService::class.java).getSearchedMovies(query = query, page = page)
            .runCatching { body() }


    override suspend fun saveFavoriteMovie(favMovie: FavoriteMovieEntity) =
        roomDatabaseInstance.favoriteMoviesDao().insertFavoriteMovie(favMovie)

    override suspend fun fetchAllFavoriteMovies(): List<FavoriteMovieEntity> =
        roomDatabaseInstance.favoriteMoviesDao().getAllFavoriteMovies()

    override suspend fun deleteFavoriteMovie(title: String) =
        roomDatabaseInstance.favoriteMoviesDao().deleteFavMovieTitle(title)


}