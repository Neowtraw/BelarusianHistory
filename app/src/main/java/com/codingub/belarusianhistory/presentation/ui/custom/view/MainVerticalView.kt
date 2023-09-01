package com.codingub.belarusianhistory.presentation.ui.custom.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.utils.DrawableUtil
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.textSizeDp

@SuppressLint("ViewConstructor")
class MainVerticalView(
    context: Context,
    src: Int,
    textName: String,
    textInfo: String,
    textAchieves: String
) : LinearLayoutCompat(context) {


    private val img: AppCompatImageView
    private val tvName: TextView
    private val tvInfo: TextView
    private val tvAchieves: TextView

    private val indent: Int = 16.dp

    private val gradient: GradientDrawable

    init{
        setPadding(indent)
        setWillNotDraw(false)
        orientation = VERTICAL
        gravity = Gravity.CENTER_VERTICAL

        tvName = TextView(context).apply {
            text = textName
            textSizeDp = 20f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        addView(tvName, LayoutParams(
            LayoutParams.MATCH_PARENT,
            0,
            1f
        ))


        tvInfo = TextView(context).apply {
            text = textInfo
            textSizeDp = 15f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        addView(tvInfo, LayoutParams(
            LayoutParams.MATCH_PARENT,
            0,
            3f
        ))

        img = AppCompatImageView(context).apply {
            setImageResource(src)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        addView(img, LayoutParams(
            LayoutParams.WRAP_CONTENT,
            0,
            2f
        ))


        tvAchieves = TextView(context).apply {
            //изменяем
            text = textAchieves
            textSizeDp = 20f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        addView(tvAchieves, LayoutParams(
            LayoutParams.MATCH_PARENT,
            0,
            1f
        ))

        gradient = DrawableUtil.createGradient(
            R.color.top_color_achieves,
            R.color.bottom_color_achieves, GradientDrawable.Orientation.TOP_BOTTOM).apply {
            cornerRadius = resources.getDimension(R.dimen.corner_icon_radius)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        gradient.setBounds(0, 0, width, height)
        if (canvas != null) {
            gradient.draw(canvas)
        }
    }

}