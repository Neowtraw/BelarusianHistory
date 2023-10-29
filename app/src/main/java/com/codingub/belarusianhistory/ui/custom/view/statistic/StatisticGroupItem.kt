package com.codingub.belarusianhistory.ui.custom.view.statistic

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat

@SuppressLint("ViewConstructor")
class StatisticGroupItem(
    context : Context
) : LinearLayoutCompat(context){

    private val group: TextView
    private val members: TextView

    init {

        group = TextView(context).apply {

        }

        members = TextView(context).apply {

        }
    }

}