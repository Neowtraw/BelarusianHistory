package com.codingub.belarusianhistory.utils.extension

import android.content.Context
import com.codingub.belarusianhistory.sdk.Language
import java.util.Locale

fun Context.setAppLocale(language: Language): Context {
    val locale = Locale(language.code)
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    return createConfigurationContext(config)
}