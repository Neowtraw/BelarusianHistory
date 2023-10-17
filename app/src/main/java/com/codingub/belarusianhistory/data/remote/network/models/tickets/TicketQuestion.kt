package com.codingub.belarusianhistory.data.remote.network.models.tickets


import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve
import com.codingub.belarusianhistory.data.remote.network.models.practices.PracticeQuestion
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class TicketQuestion(
    val id : String,
    val name: String,
    val info: String,

    val practices: List<PracticeQuestion> = emptyList(),
    val achieve: Achieve? = null
)