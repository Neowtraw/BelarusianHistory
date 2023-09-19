package com.codingub.belarusianhistory.sdk

import androidx.annotation.StringRes
import com.codingub.belarusianhistory.R

enum class Language(
    @StringRes private val nameRes: Int
    ) {

    Russian(
        R.string.russian
    ),
    Belarussian(
        R.string.belarussian
    )
}