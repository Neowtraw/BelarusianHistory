package com.codingub.belarusianhistory.utils

import com.codingub.belarusianhistory.presentation.ui.MainActivity

object LocaleController {

    //для локальных неизменяемых переменных
    private val strings: MutableMap<String, Int> = mutableMapOf(

    )
    fun getString(key: String) : String = MainActivity.getInstance().resources.getString(strings[key]!!)
}