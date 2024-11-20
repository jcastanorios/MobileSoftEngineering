package com.vinylsmobile.model

data class Band (
    override val id: Int,
    override val name: String,
    override val image: String,
    override val description: String,
    override val type: PerformerType = PerformerType.BAND,
    override val albums: List<Album>,
    val creationDate: String,
): IPerformer