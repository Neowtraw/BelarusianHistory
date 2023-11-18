package com.codingub.belarusianhistory.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.codingub.belarusianhistory.App
import com.codingub.belarusianhistory.sdk.AccessLevel
import javax.inject.Singleton

@Singleton
object UserConfig {

    private val key_user_token = "token"
    private val key_user_login = "login"
    private val key_user_username = "username"
    private val key_user_access_level = "access_level"

    private val prefs: SharedPreferences by lazy {
        App.getInstance().getSharedPreferences(
            "${App.getInstance().packageName}_${this::class.java.simpleName}",
            Context.MODE_PRIVATE
        )
    }
    private val editor: SharedPreferences.Editor get() = prefs.edit()


    private var token: String = ""

    fun getToken(): String = prefs.getString(key_user_token, "") ?: ""
    fun setToken(value: String) {
        token = value
        editor.putString(key_user_token, token).commit()
    }

    private var login: String = ""

    fun getLogin(): String = prefs.getString(key_user_login, "") ?:  ""
    fun setLogin(value: String){
        login = value
        editor.putString(key_user_login, login).commit()
    }

    private var username: String = ""

    fun getUsername(): String = prefs.getString(key_user_username, "") ?:  ""
    fun setUsername(value: String){
        username = value
        editor.putString(key_user_username, username).commit()
    }

    val accessLevel: MutableLiveData<AccessLevel> = MutableLiveData(AccessLevel.valueOf(prefs.getString(
        key_user_access_level,AccessLevel.User.name)!!))
    fun getAccessLevel(): AccessLevel = accessLevel.value!!
    fun setAccessLevel(level: AccessLevel){
        accessLevel.value = level
        editor.putString(key_user_access_level, level.name).apply()
    }

}