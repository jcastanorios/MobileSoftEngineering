package com.vinylsmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "performers_table")
data class Performer(
    @PrimaryKey override val id: Int,
    override val name: String,
    override val image: String,
    override val description: String,
    override val type: PerformerType,
    override val albums: List<Album>,
) : IPerformer {
    // Constructor vacío requerido por Room
    constructor() : this(0, "", "", "", PerformerType.UNKNOWN, albums = emptyList())
}