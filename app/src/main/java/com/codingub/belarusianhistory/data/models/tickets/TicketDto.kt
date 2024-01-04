package com.codingub.belarusianhistory.data.models.tickets

import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TicketDto(
    @SerializedName("id") val id: String = UUID.randomUUID().toString(),
    @SerializedName("name") val name: String,
    @SerializedName("timer") val timer: Long, //for timer in ticket
    @SerializedName("tqs") var tqs: List<TicketQuestionDto> = emptyList(),
    @SerializedName("achievement") val achievement: AchieveDto? = null
)

