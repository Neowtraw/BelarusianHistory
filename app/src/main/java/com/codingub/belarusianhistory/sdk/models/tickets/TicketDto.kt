package com.codingub.belarusianhistory.sdk.models.tickets

import com.codingub.belarusianhistory.sdk.models.achieves.AchieveDto
import com.google.gson.annotations.SerializedName

data class TicketDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("timer") val timer: Long, //for timer in ticket
    @SerializedName("tqs") var tqs: List<TicketQuestionDto> = emptyList(),
    @SerializedName("achievement") val achievement: AchieveDto? = null
)

