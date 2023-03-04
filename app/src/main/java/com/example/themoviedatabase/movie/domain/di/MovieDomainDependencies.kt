package com.example.themoviedatabase.movie.domain.di

import com.example.themoviedatabase.movie.domain.MovieDomainLayerContract
import com.example.themoviedatabase.movie.domain.model.Movies
import com.example.themoviedatabase.movie.domain.usecase.GetAllPopularMoviesUC
import com.example.themoviedatabase.movie.domain.usecase.GetPopularMoviesNextPageUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

@Module
@InstallIn(SingletonComponent::class)
object MovieDomainModule {

    @Provides
    @Named("get_popular_movies")
    fun providesGetAllPopularMoviesUC(allMoviesUC: GetAllPopularMoviesUC) : @JvmSuppressWildcards MovieDomainLayerContract.PresentationLayer.UseCase<Movies> = allMoviesUC

    @Provides
    @Named("get_popular_movies_next_page")
    fun providesGetPopularMoviesNextPageUC(getNextPageUC: GetPopularMoviesNextPageUC) : @JvmSuppressWildcards MovieDomainLayerContract.PresentationLayer.UseCase<Movies> = getNextPageUC
}