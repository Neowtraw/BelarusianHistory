package com.codingub.belarusianhistory.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.codingub.belarusianhistory.App
import com.codingub.belarusianhistory.sdk.Language
import com.codingub.belarusianhistory.sdk.ThemeType


object ApplicationConfig {

    private val key_saved_theme: String = "savedTheme"
    private val key_saved_language: String = "savedLanguage"

    private val prefs: SharedPreferences =
        App.getInstance().getSharedPreferences(
            "${App.getInstance().packageName}_${this::class.java.simpleName}",
            Context.MODE_PRIVATE
        )

    private val editor: SharedPreferences.Editor get() = prefs.edit()

    //сохранение темы
    private val savedTheme: MutableLiveData<ThemeType> = MutableLiveData(ThemeType.valueOf(prefs.getString(key_saved_theme,ThemeType.DEFAULT.name)!!))

    fun getTheme(): ThemeType = savedTheme.value!!
    fun setTheme(theme: ThemeType){
        savedTheme.value = theme
        editor.putString(key_saved_theme, theme.name).apply()
    }

    private val savedLanguage: MutableLiveData<Language> =
        MutableLiveData(Language.valueOf(prefs.getString(key_saved_language, Language.Russian.name)!!))
    fun getLanguage(): Language = savedLanguage.value!!

    fun setLanguage(language: Language){
        savedLanguage.value = language
        editor.putString(key_saved_language, language.name).apply()
    }


}