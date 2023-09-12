package com.codingub.belarusianhistory.data.local.db

import androidx.room.TypeConverter
import com.codingub.belarusianhistory.sdk.TaskType

object Converter {

    @TypeConverter
    @JvmStatic
    fun toTaskType(value: String) : TaskType {
        return TaskType.fromValue(value.toInt())
    }

    @TypeConverter
    @JvmStatic
    fun fromTaskType(value: TaskType) : String {
        return value.name
    }

}