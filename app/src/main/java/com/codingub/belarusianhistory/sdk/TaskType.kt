package com.codingub.belarusianhistory.sdk

import androidx.annotation.StringRes

enum class TaskType(
    private val task: Int
) {

    Test(0),
    DateOrder(1),
    Connection(2)
}