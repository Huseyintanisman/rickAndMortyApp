package com.huseyin.rickandmortyapi.model

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object Converters {
    @TypeConverter
    fun fromString(value: String?): Location {
        val type: Type = object : TypeToken<Location?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromLocation(location: Location?): String {
        val gson = Gson()
        return gson.toJson(location)
    }
}