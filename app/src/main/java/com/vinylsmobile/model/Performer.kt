package com.vinylsmobile.model

import java.io.Serializable

data class Performer(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String
) : Serializable