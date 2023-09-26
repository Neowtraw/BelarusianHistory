package com.codingub.belarusianhistory.utils

import android.graphics.Color
import androidx.annotation.FloatRange

object ColorUtil {


    //изменение яркости
    fun darkenColor(color: Int, @FloatRange(from = 0.0, to = 1.0) value: Float): Int{
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        if(hsv[2] - value > 0){
            hsv[2] -= value
        } else hsv[2] = 0f

        return Color.HSVToColor(hsv)
    }

    fun lightenColor(color: Int, @FloatRange(from = 0.0, to = 1.0) value: Float): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)

        if (hsv[2] + value < 1) {
            hsv[2] += value
        } else {
            hsv[2] = 1f
        }

        return Color.HSVToColor(hsv)
    }

    fun overlayColor(color: Int, @FloatRange(from = 0.0, to = 1.0) value: Float) : Int{
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)

        return if (hsv[2] > 0.5f) darkenColor(color, value)
        else lightenColor(color, value)
    }

}