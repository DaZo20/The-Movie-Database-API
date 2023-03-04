package com.example.themoviedatabase.movie.domain.usecase

import com.example.themoviedatabase.movie.domain.MovieDomainLayerContract
import com.example.themoviedatabase.movie.domain.model.Movies
import javax.inject.Inject

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

class GetAllPopularMoviesUC @Inject constructor(
    private val movieRepository: MovieDomainLayerContract.DataLayer.MovieRepository
) : MovieDomainLayerContract.PresentationLayer.UseCase<Movies> {
    override suspend fun invoke(): Result<Movies> = movieRepository.getAllPopularMovies()

}