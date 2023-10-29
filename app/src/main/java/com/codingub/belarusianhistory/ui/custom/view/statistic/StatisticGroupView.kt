package com.codingub.belarusianhistory.ui.custom.view.statistic

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat

/**
 * [StatisticGroupView] shows user's groups in the [StatisticFragment]
 */
@SuppressLint("ViewConstructor")
class StatisticGroupView(
    context: Context
) : LinearLayoutCompat(context){

    private val groups: TextView
    private val add: ImageView
    private val reset: ImageView

    init {

        groups = TextView(context).apply {

        }

        add = ImageView(context).apply {

        }

        reset = ImageView(context).apply {

        }
    }


}