package com.codingub.belarusianhistory.sdk

import androidx.annotation.StringRes
import com.codingub.belarusianhistory.R
import kotlinx.serialization.Serializable

enum class AchieveType(
    @StringRes val nameRes: Int
) {

    PRACTICE(
        nameRes = R.string.practice
    ),
    TICKET(
        nameRes = R.string.tickets
    )
}