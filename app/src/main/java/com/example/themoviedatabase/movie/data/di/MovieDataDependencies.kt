package com.example.themoviedatabase.movie.data.di

import com.example.themoviedatabase.movie.data.datasource.MovieDataSource
import com.example.themoviedatabase.movie.data.datasource.TMDBMovieDataSource
import com.example.themoviedatabase.movie.data.repository.TMDBMovieRepository
import com.example.themoviedatabase.movie.domain.MovieDomainLayerContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

@Module
@InstallIn(SingletonComponent::class)
object MovieDataModule {

    @Provides
    fun provideMovieRemoteRepository(
        remoteDataSource: MovieDataSource.Remote,
        localDataSource: MovieDataSource.Local
    ): MovieDomainLayerContract.DataLayer.MovieRepository =
        TMDBMovieRepository.apply{
            moviesRemoteDataSource = remoteDataSource
            moviesLocalDataSource = localDataSource
        }

    @Provides
    fun providesRemoteMovieDataSource(movieDataSource: TMDBMovieDataSource): MovieDataSource.Remote = movieDataSource

    @Provides
    fun providesLocalMovieDataSource(movieDataSource: TMDBMovieDataSource): MovieDataSource.Local = movieDataSource
}