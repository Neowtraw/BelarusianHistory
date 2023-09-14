package com.codingub.belarusianhistory.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.codingub.belarusianhistory.App

object Resource {

    private val context: Context get() = App.getInstance()

    fun color(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

    fun string(@StringRes stringKey: Int): String = context.resources.getString(stringKey)
    fun string(@StringRes stringKey: Int, vararg args: Any): String = String.format(string(stringKey), *args)

    fun dimen(@DimenRes id: Int): Float = context.resources.getDimension(id)
}