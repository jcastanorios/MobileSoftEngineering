package com.vinylsmobile.model
import androidx.room.*

@Entity(tableName = "albums_table")
data class Album (
    @PrimaryKey val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String
)