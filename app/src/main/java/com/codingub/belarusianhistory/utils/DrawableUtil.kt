package com.codingub.belarusianhistory.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.codingub.belarusianhistory.presentation.ui.MainActivity

object DrawableUtil {
    private val context: Context get() = MainActivity.getInstance()

    fun createGradient(
        firstColor: Int,
        secondColor: Int,
        orientation: GradientDrawable.Orientation
    ): GradientDrawable {

        return GradientDrawable(orientation, intArrayOf(
            ContextCompat.getColor(context, firstColor),
            ContextCompat.getColor(context, secondColor)))
    }

    fun rect(@ColorInt color: Int, outline: Outline? = null, corner: Float): Drawable {
        return rect(color, outline, corners = FloatArray(4) { corner })
    }

    fun rect(outline: Outline, corner: Float): Drawable {
        return rect(
            color = Color.TRANSPARENT,
            outline = outline,
            corner = corner
        )
    }

    fun rect(@ColorInt color: Int, outline: Outline? = null, corners: FloatArray?): Drawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(color)
            outline?.let {
                setStroke(it.width, it.color)
            }
            corners?.let {
                cornerRadii = createCorners(it)
            }
        }
    }

    private fun createCorners(corners: FloatArray): FloatArray {
        val a = FloatArray(8)
        for (i in corners.indices) {
            a[i * 2] = corners[i]
            a[i * 2 + 1] = corners[i]
        }

        return a
    }


    class Outline(@ColorInt val color: Int, val width: Int)

}