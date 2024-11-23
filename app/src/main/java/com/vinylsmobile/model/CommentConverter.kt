package com.vinylsmobile.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CommentConverter {

    @TypeConverter
    fun fromCommentList(comments: List<Comment>?): String? {
        val gson = Gson()
        return gson.toJson(comments)
    }

    @TypeConverter
    fun toCommentList(data: String?): List<Comment>? {
        val gson = Gson()
        val type = object : TypeToken<List<Comment>>() {}.type
        return gson.fromJson(data, type)
    }
}
