package com.vinylsmobile.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vinylsmobile.model.CollectorAlbums

class CollectorConverter {
    @TypeConverter
    fun fromCollectorAlbumsList(collectorAlbums: List<CollectorAlbums>): String {
        val gson = Gson()
        return gson.toJson(collectorAlbums)
    }

    @TypeConverter
    fun toCollectorAlbumsList(data: String): List<CollectorAlbums> {
        val gson = Gson()
        val listType = object : TypeToken<List<CollectorAlbums>>() {}.type
        return gson.fromJson(data, listType)
    }
}