package com.codingub.belarusianhistory.network.models.userdata

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Results(
    @Json(name = "id") val id : String,
    @Json(name = "type")  val type : Int, //type of result -> 1- Ticket 2- Practice
    @Json(name = "typeId") val typeId: String //id of object (Ticket/Practice)
)
