package com.example.themoviedatabase.common.di

import android.content.Context
import androidx.room.Room
import com.example.themoviedatabase.common.db.ApplicationDatabase
import com.example.themoviedatabase.common.utils.getRetrofitInstance
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Singleton
    @Provides
    fun provideRetrofit(converterFactory: Converter.Factory): Retrofit =
        getRetrofitInstance(converterFactory = converterFactory)


    @Singleton
    @Provides
    fun provideGsonConverterFactory(): Converter.Factory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun providesRoomDatabaseInstance(@ApplicationContext applicationContext: Context) : ApplicationDatabase =
        Room.databaseBuilder(
            applicationContext,
            ApplicationDatabase::class.java,"the-movie-database-db"
        ).build()
}