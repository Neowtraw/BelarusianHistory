package com.codingub.belarusianhistory.data.remote.network.models.achieves


data class Results(
    val id : String,
    val type : Int, //type of result -> 1- Ticket 2- Practice
    val typeId: String, //id of object (Ticket/Practice)
    val achieveId: String
    //нужно добавить логику проверки с achieve
)