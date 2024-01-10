package com.codingub.belarusianhistory.data.models.tickets


import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TicketQuestionDto(
    @SerializedName("id") val id : String = UUID.randomUUID().toString(),
    @SerializedName("name") var name: String,
    @SerializedName("info") var info: String,

    @SerializedName("practices") val practices: List<PracticeQuestion> = emptyList(),
    @SerializedName("achieve") var achieve: AchieveDto? = null
)