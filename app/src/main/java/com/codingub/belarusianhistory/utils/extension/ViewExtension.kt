package com.codingub.belarusianhistory.utils.extension

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.codingub.belarusianhistory.ui.custom.ScaleTouchListener

/*
    View
 */
fun View.setPaddingDp(left: Int, top: Int, right: Int, bottom: Int) {
    setPadding(left.dp, top.dp, right.dp, bottom.dp)
}

fun View.applyScaleTouch() = setOnTouchListener(ScaleTouchListener())

/*
    TextView
 */

var TextView.textSizeDp: Float
    get() = textSize.px
    set(value) = setTextSize(TypedValue.COMPLEX_UNIT_DIP, value)

/*
    ImageView
 */