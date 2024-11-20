package com.vinylsmobile.model

enum class PerformerType {
    ARTIST,
    BAND,
    UNKNOWN
}

interface IPerformer {
    val id: Int
    val name: String
    val image: String
    val description: String
    val type: PerformerType
    val albums: List<Album>
}