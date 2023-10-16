package com.codingub.belarusianhistory.sdk

import androidx.annotation.StringRes
import com.codingub.belarusianhistory.R

enum class AccessLevel(val position: Int, @StringRes val type: Int){
    User(0, R.string.user),
    Teacher(1, R.string.teacher),
    Admin(2, R.string.admin)
}
