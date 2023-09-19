package com.codingub.belarusianhistory.sdk

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import com.codingub.belarusianhistory.R

enum class ThemeType(
    val id: Int,
    @StringRes val nameRes: Int,
    val nightMode: Int

) {
    DEFAULT(0, R.string.default_theme, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    LIGHT(1, R.string.light_theme, AppCompatDelegate.MODE_NIGHT_NO),
    DARK(2, R.string.dark_theme, AppCompatDelegate.MODE_NIGHT_YES);

    companion object {
        fun getAllThemes(): List<ThemeType> {
            return enumValues<ThemeType>().toList()
        }
    }
}