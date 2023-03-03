package com.example.themoviedatabase.common.utils

import retrofit2.Converter
import retrofit2.Retrofit

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

private const val DEFAULT_BASE_URL: String = "https://api.themoviedb.org/3/"

fun getRetrofitInstance(baseUrl: String = DEFAULT_BASE_URL, converterFactory: Converter.Factory) = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(converterFactory)
    .build()