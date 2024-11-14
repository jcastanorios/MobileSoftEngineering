package com.vinylsmobile.model

data class Performer (
    override val id: Int,
    override val name: String,
    override val image: String,
    override val description: String,
    override val type: PerformerType,
): IPerformer