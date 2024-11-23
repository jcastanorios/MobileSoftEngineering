package com.vinylsmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String,
    val rating: Int,
    val albumId:Int
)