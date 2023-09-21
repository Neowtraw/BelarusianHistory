package com.codingub.belarusianhistory

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    init{
        Instance = this
    }

    companion object {
        private var Instance: App? = null
        fun getInstance(): App = Instance!!
    }

//    override fun onCreate() {
//        super.onCreate()
//        Instance = this
//
// //       setLanguage(ApplicationConfig.getLanguage())
//    }

//    fun setLanguage(language: Language) {
//        with(resources) {
//            val currentConfig: Configuration = resources.configuration
//            val locale = Locale(language.code)
//            configuration.setLocale(locale)
//            updateConfiguration(configuration, displayMetrics)
//
//
//        }
//    }
}