package com.codingub.belarusianhistory.ui.custom.view.statistic

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.textSizeDp

@SuppressLint("ViewConstructor")
class StatisticGroupItem(
    context : Context
) : RelativeLayout(context){

    private val group: TextView
    private val members: TextView

    var title: String
        get() = group.text.toString()
        set(value) {
            group.text = value
        }

    var participants: String
        get() = members.text.toString()
        set(value) {
            members.text = value + " " + Resource.string(R.string.participants_reduction)
        }

    init {
        setWillNotDraw(false)
        gravity = Gravity.CENTER_VERTICAL

        group = TextView(context).apply {
            typeface = Font.REGULAR
            textSizeDp = 7f.dp
            isSingleLine = true
            text = "group test!!!"
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(Resource.color(R.color.text_color))
            gravity = Gravity.START
        }
        members = TextView(context).apply {
            typeface = Font.REGULAR
            textSizeDp = 7f.dp
            isSingleLine = true
            text = "member test!!"
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(Resource.color(R.color.text_color))
        }

        addView(group, LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            setPadding(0,0,10.dp,0)
        })

        addView(members, LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(ALIGN_PARENT_RIGHT, TRUE)
        })
    }
}