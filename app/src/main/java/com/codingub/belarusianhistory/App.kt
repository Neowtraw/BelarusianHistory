package com.codingub.belarusianhistory

import android.app.Application
import android.content.res.Configuration
import android.util.Log
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
    }

    fun setLanguage(language: Language) {
        with(resources) {
            val locale = Locale(language.code)
            configuration.setLocale(locale)
            updateConfiguration(configuration, displayMetrics)
        }
    }
}