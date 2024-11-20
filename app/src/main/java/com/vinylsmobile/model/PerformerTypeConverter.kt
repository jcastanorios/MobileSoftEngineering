package com.vinylsmobile.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PerformerTypeConverter {
    @TypeConverter
    fun fromPerformerType(type: PerformerType): String {
        return type.name
    }

    @TypeConverter
    fun toPerformerType(type: String): PerformerType {
        return PerformerType.valueOf(type)
    }

    @TypeConverter
    fun fromAlbumsList(albums: List<Album>?): String? {
        val gson = Gson()
        return gson.toJson(albums)
    }

    @TypeConverter
    fun toAlbumsList(albumsJson: String?): List<Album>? {
        val gson = Gson()
        val type = object : TypeToken<List<Album>>() {}.type
        return gson.fromJson(albumsJson, type)
    }
}
