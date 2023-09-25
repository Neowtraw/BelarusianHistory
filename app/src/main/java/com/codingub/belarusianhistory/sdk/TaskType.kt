package com.codingub.belarusianhistory.sdk

import androidx.annotation.StringRes

enum class TaskType(
    private val task: Int
) {

    Test(0),
    InputText(1),
    DateOrder(2);

    companion object {
        private val map = values().associateBy(TaskType::task)

        fun fromValue(value: Int): TaskType {
            return map[value]
                ?: throw IllegalArgumentException("No enum constant with task value: $value")
        }
    }
}