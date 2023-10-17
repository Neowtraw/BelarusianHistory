package com.codingub.belarusianhistory.data.remote.network.models.achieves

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Results(
    val id : String,
    val type : Int, //type of result -> 1- Ticket 2- Practice
    val typeId: String //id of object (Ticket/Practice)
)