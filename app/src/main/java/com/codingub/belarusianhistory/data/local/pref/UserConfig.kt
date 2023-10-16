package com.codingub.belarusianhistory.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.codingub.belarusianhistory.App
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
object UserConfig {

    private val key_user_token = "Token"

    private val prefs: SharedPreferences =
        App.getInstance().getSharedPreferences(
            "${App.getInstance().packageName}_${this::class.java.simpleName}",
            Context.MODE_PRIVATE
        )
    private val editor: SharedPreferences.Editor get() = prefs.edit()


    // аутентефикация
    private val token: MutableLiveData<String> = MutableLiveData()

    fun getToken(): String = token.value!!
    fun setToken(value: String) {
        token.value = value
        editor.putString(key_user_token, token.value).apply()
    }


}