package com.codingub.belarusianhistory.data.local.db

import androidx.room.TypeConverter
import com.codingub.belarusianhistory.sdk.TaskType

object Converter {

    @TypeConverter
    fun toTaskType(value: String) : TaskType {
        return TaskType.valueOf(value)
    }

    @TypeConverter
    fun fromTaskType(value: TaskType) : String {
        return value.name
    }

}