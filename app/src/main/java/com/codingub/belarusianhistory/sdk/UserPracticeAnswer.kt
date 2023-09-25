package com.codingub.belarusianhistory.sdk

import com.codingub.belarusianhistory.domain.model.Answer

//Additional data class

data class UserPracticeAnswer(
    var pqInfo : String,
    var userAnswer: List<String>,
    var rightAnswer: List<String>,
    val isRight: Boolean
    )
