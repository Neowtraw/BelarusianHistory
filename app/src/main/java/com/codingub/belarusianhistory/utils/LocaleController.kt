package com.codingub.belarusianhistory.utils

import com.codingub.belarusianhistory.presentation.ui.MainActivity

object LocaleController {

    //сюда записываем все стринги
    private val strings: MutableMap<String, Int> = mutableMapOf(

    )

    fun getString(key: String) : String = MainActivity.getInstance().resources.getString(strings[key]!!)
}