package com.codingub.belarusianhistory.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.codingub.belarusianhistory.App

object DrawableUtil {
    private val context: Context get() = App.getInstance()

    fun createGradient(
        firstColor: Int,
        secondColor: Int,
        orientation: GradientDrawable.Orientation
    ): GradientDrawable {

        return GradientDrawable(orientation, intArrayOf(
            ContextCompat.getColor(context, firstColor),
            ContextCompat.getColor(context, secondColor)))
    }


}