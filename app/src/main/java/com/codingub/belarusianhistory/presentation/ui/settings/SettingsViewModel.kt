package com.codingub.belarusianhistory.presentation.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.data.local.pref.ApplicationConfig
import com.codingub.belarusianhistory.presentation.ui.MainActivity
import com.codingub.belarusianhistory.sdk.Language
import com.codingub.belarusianhistory.sdk.ThemeType


class SettingsViewModel : ViewModel() {

    var themeList: List<ThemeType> = ThemeType.getAllThemes()

    fun getLastThemePos() : Int{
        return ApplicationConfig.getTheme().id
    }

    fun setNewTheme(theme: ThemeType){
        ApplicationConfig.setTheme(theme)

        AppCompatDelegate.setDefaultNightMode(theme.nightMode)
        MainActivity.getInstance().recreate()
    }

    fun setNewLanguage(language: Language){
        return ApplicationConfig.setLanguage(language)
    }

    fun getLastLanguagePos(): Int{
        return ApplicationConfig.getLanguage().id
    }

}