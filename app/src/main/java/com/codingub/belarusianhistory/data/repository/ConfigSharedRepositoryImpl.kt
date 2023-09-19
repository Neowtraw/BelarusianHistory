package com.codingub.belarusianhistory.data.repository

import com.codingub.belarusianhistory.data.local.pref.ApplicationConfig
import com.codingub.belarusianhistory.domain.repository.ConfigSharedRepository
import com.codingub.belarusianhistory.sdk.Language
import com.codingub.belarusianhistory.sdk.ThemeType
import javax.inject.Inject

class ConfigSharedRepositoryImpl @Inject constructor(private var appConfig: ApplicationConfig) : ConfigSharedRepository {

    override fun getSavedThemeType(): ThemeType {
        return appConfig.getTheme()
    }

    override fun getSavedLanguage(): Language {
        return appConfig.getLanguage()
    }

    override fun setSavedTheme(theme: ThemeType) {
        return appConfig.setTheme(theme)
    }

    override fun setSavedLanguage(language: Language) {
        return appConfig.setLanguage(language)
    }
}