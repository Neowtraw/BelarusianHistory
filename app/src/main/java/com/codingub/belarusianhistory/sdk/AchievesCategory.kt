package com.codingub.belarusianhistory.sdk

import androidx.annotation.StringRes
import com.codingub.belarusianhistory.R

enum class AchievesCategory(
    @StringRes val nameRes: Int
) {

    Practice(
        nameRes = R.string.practice
    ),
    Tickets(
        nameRes = R.string.tickets
    )
}