package com.vinylsmobile.model

enum class PerformerType {
    ARTIST,
    BAND
}

interface IPerformer {
    val id: Int
    val name: String
    val image: String
    val description: String
    val type: PerformerType
}