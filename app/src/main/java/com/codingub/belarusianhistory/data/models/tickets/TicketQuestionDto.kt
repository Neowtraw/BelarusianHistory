package com.codingub.belarusianhistory.data.models.tickets


import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion
import com.google.gson.annotations.SerializedName

data class TicketQuestionDto(
    @SerializedName("id") val id : String,
    @SerializedName("name") val name: String,
    @SerializedName("info") val info: String,

    @SerializedName("practices") val practices: List<PracticeQuestion> = emptyList(),
    @SerializedName("achieve") val achieve: AchieveDto? = null
)