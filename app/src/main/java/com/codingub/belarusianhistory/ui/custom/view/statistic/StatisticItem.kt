package com.codingub.belarusianhistory.ui.custom.view.statistic

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.textSizeDp

@SuppressLint("ViewConstructor")
class StatisticItem(
    title: String,
    firstValue: Int,
    secondValue: Int,
    context: Context
) : LinearLayoutCompat(context) {

    private val checkmark: ImageView
    private val titleView: TextView

    init {
        setWillNotDraw(false)
        orientation = HORIZONTAL

        checkmark = ImageView(context).apply {
            post {
                setImageResource(R.drawable.checkmark)
                setColorFilter(Resource.color(R.color.contrast))
            }
        }
        addView(checkmark, LayoutParams(
            20.dp,
            20.dp,
        ).apply {
            gravity = Gravity.CENTER
            setMargins(0, 0, 10.dp, 0)
        })

        titleView = TextView(context).apply {
            text = "$title: $firstValue/$secondValue"
            typeface = Font.REGULAR
            textSizeDp = 14f
            setTextColor(Resource.color(R.color.text_color))
            gravity = Gravity.START
        }
        addView(titleView, LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT,
        ).apply {
            setPadding(10.dp, 0, 0, 0)
        })

    }

}