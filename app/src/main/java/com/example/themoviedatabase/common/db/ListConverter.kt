package com.example.themoviedatabase.common.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//
// Created by DaZo20 on 03/03/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

class ReferenceListConverter {

    @TypeConverter
    fun fromReferenceList(value: List<Int>): Int {
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.toJson(value,type).toInt()
    }

    @TypeConverter
    fun toReferenceList(value: Int): List<Int> {
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value.toString(), type)
    }

}