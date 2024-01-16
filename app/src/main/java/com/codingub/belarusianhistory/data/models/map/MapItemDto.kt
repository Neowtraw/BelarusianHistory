package com.codingub.belarusianhistory.data.models.map

data class MapItemDto(
    val id: String,
    val x: Float,
    val y: Float,
    val title: String,
    val description: String,
    val animation: String? = null,
    val image: String? = null,
    val creatorId: String,
    val mapId: String
)