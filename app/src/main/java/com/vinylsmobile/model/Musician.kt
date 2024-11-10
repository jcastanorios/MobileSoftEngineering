package com.vinylsmobile.model

data class Musician (
    override val id: Int,
    override val name: String,
    override val image: String,
    override val description: String,
    val birthDate: String
): IPerformer