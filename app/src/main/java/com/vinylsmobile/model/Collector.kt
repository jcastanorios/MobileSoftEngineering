package com.vinylsmobile.model

import androidx.room.*
@Entity(tableName = "collectors_table")
data class Collector (
    @PrimaryKey val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
)