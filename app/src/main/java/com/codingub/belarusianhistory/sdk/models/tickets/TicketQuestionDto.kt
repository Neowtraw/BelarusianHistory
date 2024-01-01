package com.codingub.belarusianhistory.sdk.models.tickets


import com.codingub.belarusianhistory.sdk.models.achieves.AchieveDto
import com.codingub.belarusianhistory.sdk.models.practices.PracticeQuestion
import com.google.gson.annotations.SerializedName

data class TicketQuestionDto(
    @SerializedName("id") val id : String,
    @SerializedName("name") val name: String,
    @SerializedName("info") val info: String,

    @SerializedName("practices") val practices: List<PracticeQuestion> = emptyList(),
    @SerializedName("achieve") val achieve: AchieveDto? = null
)