package com.example.themoviedatabase.movie.domain.usecase

import com.example.themoviedatabase.movie.domain.MovieDomainLayerContract
import com.example.themoviedatabase.movie.domain.model.Movies
import javax.inject.Inject

//
// Created by DaZo20 on 16/06/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
class GetPopularMoviesByNameUC @Inject constructor(
    private val movieRepository: MovieDomainLayerContract.DataLayer.MovieRepository
){
    suspend operator fun invoke(name: String): Result<Movies> = movieRepository.getPopularMoviesByName(name)
}