package com.codingub.belarusianhistory.domain.repository

import com.codingub.belarusianhistory.sdk.Language
import com.codingub.belarusianhistory.sdk.ThemeType

interface ConfigSharedRepository {

    fun getSavedThemeType(): ThemeType

    fun getSavedLanguage(): Language

    fun setSavedTheme(theme: ThemeType)

    fun setSavedLanguage(language: Language)
}