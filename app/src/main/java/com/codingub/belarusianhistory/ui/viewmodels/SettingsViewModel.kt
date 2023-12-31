package com.codingub.belarusianhistory.ui.viewmodels

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.data.local.prefs.ApplicationConfig
import com.codingub.belarusianhistory.ui.MainActivity
import com.codingub.belarusianhistory.sdk.Language
import com.codingub.belarusianhistory.sdk.ThemeType


class SettingsViewModel : ViewModel() {

    var themeList: List<ThemeType> = ThemeType.getAllThemes()

    fun getLastThemePos(): Int {
        return ApplicationConfig.getTheme().id
    }

    fun setNewTheme(theme: ThemeType) {
        ApplicationConfig.setTheme(theme)

        AppCompatDelegate.setDefaultNightMode(theme.nightMode)
        MainActivity.getInstance().recreate()
    }

    fun setNewLanguage(language: Language) {
        ApplicationConfig.setLanguage(language)

    }

    fun getLastLanguagePos(): Int {
        return ApplicationConfig.getLanguage().ordinal
    }

}