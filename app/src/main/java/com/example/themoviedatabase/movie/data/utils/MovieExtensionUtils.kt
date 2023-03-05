package com.example.themoviedatabase.movie.data.utils

import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.data.model.MovieDto
import com.example.themoviedatabase.movie.data.model.MoviesDto
import com.example.themoviedatabase.movie.domain.model.Movie
import com.example.themoviedatabase.movie.domain.model.Movies

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

const val DEFAULT_INT = 1
const val DEFAULT_DOUBLE = 0.0
const val DEFAULT_STRING = ""
val DEFAULT_LIST_INT: List<Int> = listOf()

fun MoviesDto?.toMovies(): Movies =
    Movies(results = this?.results?.toMovieList() ?: emptyList())

fun List<MovieDto>.toMovieList(): List<Movie> =
    this.map { dto ->
        with(dto) {
            Movie(
                adult = adult,
                backdrop_path = backdrop_path,
                genre_ids = genre_ids,
                id = id,
                original_language = original_language,
                original_title = original_title,
                overview = overview,
                popularity = popularity,
                poster_path = poster_path,
                release_date = release_date,
                title = title,
                video = video,
                vote_average = vote_average,
                vote_count = vote_count
            )
        }
    }

fun MoviesDto.toMovieEntity(): List<FavoriteMovieEntity> =
    results.map { dto ->
        with(dto) {
            FavoriteMovieEntity(
                id  = id,
                overview = overview,
                poster_path = poster_path,
                release_date = release_date ?: "",
                title = title,
                vote_average = vote_average.toString()
            )
        }
    }

fun List<FavoriteMovieEntity>.toMovies(): Movies =
    Movies(
        results = this.map { entity ->
            with(entity) {
                Movie(
                    adult = false,
                    backdrop_path = DEFAULT_STRING,
                    genre_ids = listOf(),
                    id = id,
                    original_language = DEFAULT_STRING,
                    original_title = DEFAULT_STRING,
                    overview = overview,
                    popularity = DEFAULT_DOUBLE,
                    poster_path = poster_path,
                    release_date = release_date,
                    title = title,
                    video = false,
                    vote_average = DEFAULT_DOUBLE,
                    vote_count = DEFAULT_INT
                )
            }
        }
    )

