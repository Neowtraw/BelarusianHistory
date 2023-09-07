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
import com.codingub.belarusianhistory.sdk.FragmentType
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.DrawableUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ImageUtil
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.textSizeDp

@SuppressLint("ViewConstructor")
class MainSquareView(
    context: Context,
    src: String,
    textName: String,
    textAchieves: String,
    topColor: Int,
    bottomColor: Int
) : LinearLayoutCompat(context){

    private val img: AppCompatImageView
    private val tvName: TextView
    private val tvAchieves: TextView

    private val indent: Int = 16.dp

    private val gradient: GradientDrawable

    init{
        setPadding(indent)
        setWillNotDraw(false)
        orientation = VERTICAL
        gravity = Gravity.CENTER_VERTICAL

        img = AppCompatImageView(context).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
        ImageUtil.load(AssetUtil.menuImageUri(src)){
            img.apply {
                setImageDrawable(it)
                this@MainSquareView.invalidate()
            }
        }

        addView(img, LayoutParams(
            LayoutParams.WRAP_CONTENT,
            0,
            2f
        ))

        tvName = TextView(context).apply {
            text = textName
            textSizeDp = 17f
            gravity = Gravity.CENTER
            typeface = Font.EXTRABOLD
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        addView(tvName, LayoutParams(
            LayoutParams.MATCH_PARENT,
            0,
            1f
        ))

        tvAchieves = TextView(context).apply {
            text = textAchieves
            textSizeDp = 20f
            gravity = Gravity.CENTER
            typeface = Font.REGULAR
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        addView(tvAchieves, LayoutParams(
            LayoutParams.MATCH_PARENT,
            0,
            1f
        ))

        gradient = DrawableUtil.createGradient(topColor, bottomColor,
            GradientDrawable.Orientation.TOP_BOTTOM).apply {
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