package com.codingub.belarusianhistory.sdk

import androidx.annotation.StringRes
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.utils.Resource

enum class Language(
    val id: Int,
    @StringRes val nameRes: Int,
    @StringRes val codeRes: Int
) {

    Russian(
        0,
        R.string.russian,
        R.string.ru
    ),
    Belarussian(
        1,
        R.string.belarussian,
        R.string.be
    );

    operator fun invoke(): String = Resource.string(nameRes)

    val code: String get() = Resource.string(codeRes)
}