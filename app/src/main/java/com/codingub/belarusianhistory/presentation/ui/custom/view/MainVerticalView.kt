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
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.DrawableUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ImageUtil
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.textSizeDp

@SuppressLint("ViewConstructor")
class MainVerticalView(
    context: Context,
    src: String,
    textName: String,
    textInfo: String,
    textAchieves: String
) : LinearLayoutCompat(context){

    private val img: AppCompatImageView
    private val tvName: TextView
    private val tvInfo: TextView
    private val tvAchieves: TextView

    private val indent: Int = 16.dp

    private val gradient: GradientDrawable

    var infoTextPassed: String
        get() = tvAchieves.text.toString()
        set(value) {
            tvAchieves.text = "$value${infoText}"
        }

    var infoText: String
        get() = tvAchieves.text.toString()
        set(value) {
            tvAchieves.text = "${infoTextPassed}/$value"
        }

    init{
        setPadding(indent)
        setWillNotDraw(false)
        orientation = VERTICAL
        gravity = Gravity.CENTER_VERTICAL

        tvName = TextView(context).apply {
            text = textName
            textSizeDp = 17f
            gravity = Gravity.CENTER
            typeface = Font.EXTRABOLD
            setTextColor(ContextCompat.getColor(context, R.color.text_color))
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
            typeface = Font.LIGHT
            setTextColor(ContextCompat.getColor(context, R.color.text_color))
        }
        addView(tvInfo, LayoutParams(
            LayoutParams.MATCH_PARENT,
            0,
            3f
        ))

        img = AppCompatImageView(context).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
        addView(img, LayoutParams(
            LayoutParams.WRAP_CONTENT,
            0,
            2f
        ))

        ImageUtil.load(AssetUtil.menuImageUri(src)){
            img.apply {
                setImageDrawable(it)
                this@MainVerticalView.invalidate()
            }
        }


        tvAchieves = TextView(context).apply {
            //изменяем
            text = textAchieves
            textSizeDp = 20f
            gravity = Gravity.CENTER
            typeface = Font.REGULAR
            setTextColor(ContextCompat.getColor(context, R.color.text_color))
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

    override fun onDraw(canvas: Canvas) {
        gradient.setBounds(0, 0, width, height)
        if (canvas != null) {
            gradient.draw(canvas)
        }
    }

}