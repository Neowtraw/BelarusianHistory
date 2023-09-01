package com.codingub.belarusianhistory.utils

import android.content.Context
import com.codingub.belarusianhistory.App

object AndroidUtil {

    private val context: Context get() = App.getInstance()
    private val density = context.resources.displayMetrics.density

    fun dp(value: Float): Float = if (value == 0f) 0f else value * density
    fun dp(value: Int): Int = if (value == 0) 0 else (value * density).toInt()

    fun px(value: Float): Float = value / density
}