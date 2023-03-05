package com.example.themoviedatabase.movie.data.api

import com.example.themoviedatabase.BuildConfig
import com.example.themoviedatabase.movie.data.model.MoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
private const val API_KEY = BuildConfig.API_KEY
private const val SPANISH = "es-ES"

interface MovieService {

    @GET("movie/popular")
    suspend fun getAllPopularMovies(@Query ("api_key") api_key: String = API_KEY,
                                    @Query("language") language: String = SPANISH,
                                    @Query("page") page: Int = 1): Response<MoviesDto?>


    @GET("search/movie")
    suspend fun getSearchedMovies(@Query ("api_key") api_key: String = API_KEY,
                                  @Query("query") query: String,
                                  @Query("language") language: String = SPANISH,
                                  @Query("page") page: Int = 1): Response<MoviesDto>

}