package com.codingub.belarusianhistory.utils

import android.graphics.Typeface
import com.codingub.belarusianhistory.App

object Font {

    private val fonts: MutableMap<String, Typeface> = mutableMapOf()

    private fun font(style: String): Typeface {
        if (!fonts.containsKey(style)) {
            fonts[style] = Typeface.createFromAsset(
                App.getInstance().assets, "font/ma_$style.ttf"
            )
        }

        return fonts[style]!!
    }

    val REGULAR: Typeface get() = font("regular")
    val EXTRABOLD: Typeface get() = font("extrabold")
    val SEMIBOLD: Typeface get() = font("semibold")
    val LIGHT: Typeface get() = font("light")
}