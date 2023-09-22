package com.codingub.belarusianhistory.utils.extension

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.codingub.belarusianhistory.sdk.Language
import java.io.Serializable
import java.util.Locale

fun Context.setAppLocale(language: Language): Context {
    val locale = Locale(language.code)
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    return createConfigurationContext(config)
}

inline fun <reified T : java.io.Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}
