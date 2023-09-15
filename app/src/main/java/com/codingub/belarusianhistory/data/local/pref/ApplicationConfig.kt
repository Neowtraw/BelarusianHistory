package com.codingub.belarusianhistory.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import com.codingub.belarusianhistory.App


class ApplicationConfig {

    private val key_firstLaunch: String = "firstLaunch"
    private val key_savedVersionCode: String = "savedVersionCode"

    private val prefs: SharedPreferences =
        App.getInstance().getSharedPreferences(
            "${App.getInstance().packageName}_${this::class.java.simpleName}",
            Context.MODE_PRIVATE
        )
    private val editor: SharedPreferences.Editor get() = prefs.edit()



}