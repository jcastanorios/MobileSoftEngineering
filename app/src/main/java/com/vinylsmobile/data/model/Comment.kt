package com.vinylsmobile.data.model

import java.io.Serializable

data class Comment(
    val id: Int,
    val description: String,
    val rating: Int
) : Serializable