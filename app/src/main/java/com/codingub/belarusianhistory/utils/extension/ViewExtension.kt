package com.codingub.belarusianhistory.utils.extension

import android.util.TypedValue
import android.view.View
import android.widget.TextView

/*
    View
 */
fun View.setPaddingDp(left: Int, top: Int, right: Int, bottom: Int) {
    setPadding(left.dp, top.dp, right.dp, bottom.dp)
}

/*
    TextView
 */

var TextView.textSizeDp: Float
    get() = textSize.px
    set(value) = setTextSize(TypedValue.COMPLEX_UNIT_DIP, value)

/*
    ImageView
 */