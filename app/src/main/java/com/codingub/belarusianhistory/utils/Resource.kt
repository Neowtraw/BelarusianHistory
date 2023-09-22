package com.codingub.belarusianhistory.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.codingub.belarusianhistory.presentation.ui.MainActivity

object Resource {

    private val context: Context get() = MainActivity.getInstance()

    fun color(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

    fun string(@StringRes stringKey: Int): String = context.resources.getString(stringKey)
    fun string(@StringRes stringKey: Int, vararg args: Any): String = String.format(string(stringKey), *args)

    fun dimen(@DimenRes id: Int): Float = context.resources.getDimension(id)

    fun drawable( drawable: String) : Int =  context.resources.getIdentifier(drawable, "drawable", context.packageName)
}