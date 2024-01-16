package com.codingub.belarusianhistory.data.models.map


data class MapDto(
    val id: String,
    val map: String,
    val period: String,
    val items: List<MapItemDto>
)