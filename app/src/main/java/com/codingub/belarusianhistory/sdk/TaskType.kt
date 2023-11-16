package com.codingub.belarusianhistory.sdk

import java.io.Serializable

enum class TaskType(
    private val task: Int
) : Serializable {

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