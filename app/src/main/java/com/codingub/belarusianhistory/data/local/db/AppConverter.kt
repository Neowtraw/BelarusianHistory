package com.codingub.belarusianhistory.data.local.db

import androidx.room.TypeConverter
import com.codingub.belarusianhistory.utils.toJsonElement
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

object AppConverter {

    @TypeConverter
    @JvmStatic
    fun toJsonElement(element: Any): String {
        val d = element.toJsonElement()
        return Json.decodeFromJsonElement(d)

    }

    @TypeConverter
    @JvmStatic
    fun fromJson(element: String): Any {
        return Json.parseToJsonElement(element)
    }
}