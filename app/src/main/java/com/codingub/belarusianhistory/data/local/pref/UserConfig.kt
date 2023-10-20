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

    private val key_user_token = "token"
    private val key_user_login = "login"

    private val prefs: SharedPreferences =
        App.getInstance().getSharedPreferences(
            "${App.getInstance().packageName}_${this::class.java.simpleName}",
            Context.MODE_PRIVATE
        )
    private val editor: SharedPreferences.Editor get() = prefs.edit()


    // аутентефикация
    private var token: String = ""

    fun getToken(): String = prefs.getString(key_user_token, "") ?: ""
    fun setToken(value: String) {
        token = value
        editor.putString(key_user_token, token).commit()
    }

    //получение данных пользователя по логину
    private var login: String = ""

    fun getLogin(): String = prefs.getString(key_user_login, "") ?:  ""
    fun setLogin(value: String){
        login = value
        editor.putString(key_user_login, login).commit()
    }


}