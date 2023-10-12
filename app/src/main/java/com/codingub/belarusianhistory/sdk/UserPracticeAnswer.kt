package com.codingub.belarusianhistory.sdk

import java.io.Serializable

//Additional data class

data class UserPracticeAnswer(
    var pqInfo : String,
    var userAnswer: List<String>,
    var rightAnswer: List<String>,
    val isRight: Boolean
) : Serializable