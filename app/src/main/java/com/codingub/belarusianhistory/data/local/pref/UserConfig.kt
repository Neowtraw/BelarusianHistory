package com.codingub.belarusianhistory.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import com.codingub.belarusianhistory.App
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserConfig @Inject constructor(@ApplicationContext private val context: Context) {

    private val UID = "userId"
    private val LOGIN = "login"
    private val USER = "user"

    private val prefs: SharedPreferences =
        App.getInstance().getSharedPreferences(
            "${App.getInstance().packageName}_${this::class.java.simpleName}",
            Context.MODE_PRIVATE
        )
    private val editor = prefs.edit()

//    private fun storeString(key: String, value: String) {
//        editor.run {
//            putString(key, value)
//            apply()
//        }
//    }
//
//    private fun storeBoolean(key: String, value: Boolean) =
//        editor.run{
//            putBoolean(key, value)
//            apply()
//        }
//
//    private fun storeLogin(key: String, value: Long){
//        editor.run {
//            putLong(key, value)
//            apply()
//        }
//    }
//
//    private fun getString(key: String) =
//        prefs.getString(key, "")
//
//    fun clearAll() =
//        editor?.run {
//            clear()
//            apply()
//        }
//
//    fun setLogin(){
//        storeBoolean(LOGIN, true)
//    }

}