package com.codingub.belarusianhistory.data.models.map

import java.util.UUID

data class MapLabelDto(
    val id: String = UUID.randomUUID().toString(),
    val x: Float,
    val y: Float,
    val title: String,
    val description: String,
    val animation: String? = null,
    val image: String? = null,
    val creatorLogin: String,
    val mapId: String
)