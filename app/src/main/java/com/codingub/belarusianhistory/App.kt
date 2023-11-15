package com.codingub.belarusianhistory

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.codingub.belarusianhistory.data.local.pref.ApplicationConfig
import com.codingub.belarusianhistory.sdk.Language
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class App : Application() {

    companion object {
        private var Instance: App? = null
        fun getInstance(): App = Instance!!
    }

    override fun onCreate() {
        super.onCreate()

        Instance = this
        setLanguage(ApplicationConfig.getLanguage())
        AppCompatDelegate.setDefaultNightMode(ApplicationConfig.getTheme().nightMode)
    }

    fun setLanguage(language: Language) {
        with(resources) {
            val locale = Locale(language.code)
            configuration.setLocale(locale)
            updateConfiguration(configuration, displayMetrics)
        }
    }
}