package com.example.themoviedatabase.movie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedatabase.movie.data.db.FavoriteMovieEntity
import com.example.themoviedatabase.movie.data.repository.TMDBMovieRepository
import com.example.themoviedatabase.movie.domain.MovieDomainLayerContract
import com.example.themoviedatabase.movie.domain.model.Movies
import com.example.themoviedatabase.movie.domain.usecase.GetPopularMoviesByNameUC
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//


class MoviesViewModel @Inject constructor(
    @Named("get_popular_movies") val getAllPopularMoviesUC: MovieDomainLayerContract.PresentationLayer.UseCase<Movies>,
    @Named("get_popular_movies_next_page") val getPopularMoviesNextPageUC: MovieDomainLayerContract.PresentationLayer.UseCase<Movies>,
    @Named("get_popular_movies_by_name") val getPopularMoviesByNameUC: GetPopularMoviesByNameUC
) : ViewModel() {

    val movies: StateFlow<Movies?>
        get() = _movies.asStateFlow()

    private var _movies: MutableStateFlow<Movies?> = MutableStateFlow(null)

    val favMovies: StateFlow<FavoriteMovieEntity?>
        get() = _favMovies.asStateFlow()

    private var _favMovies: MutableStateFlow<FavoriteMovieEntity?> = MutableStateFlow(null)

    init {
        fetchMoviesData()
    }

    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            val fav = TMDBMovieRepository.getFavoriteMovies()
            if (fav.isNotEmpty()) {
                for (item in fav) {
                    _favMovies.value = item
                }
            }
        }
    }

    private fun fetchMoviesData() {
        viewModelScope.launch {
            getAllPopularMoviesUC().onSuccess { movies ->
                _movies.update { movies }
            }.onFailure { err ->
                err.printStackTrace()
            }
        }
    }

    fun onEndOfScrollReached() {
        viewModelScope.launch {
            getPopularMoviesNextPageUC().onSuccess { movies ->
                _movies.value = movies
            }.onFailure { err ->
                err.printStackTrace()
            }
        }
    }

    fun fetchMoviesByTitle(title: String) {
        viewModelScope.launch {
            getPopularMoviesByNameUC(title).onSuccess { movies ->
                _movies.value = movies
            }.onFailure { err ->
                err.printStackTrace()
            }
        }
    }

    fun saveFavoriteMovies(movie: FavoriteMovieEntity) {
        viewModelScope.launch {
            TMDBMovieRepository.insertFavoriteMovies(movie)
        }
    }

    fun deleteFavoriteMovie(title: String) {
        viewModelScope.launch {
            TMDBMovieRepository.deleteFavoriteMovies(title)
        }
    }
}