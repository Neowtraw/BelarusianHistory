package com.codingub.belarusianhistory.presentation.ui.custom.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.utils.DrawableUtil
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.textSizeDp

@SuppressLint("ViewConstructor")
class MainHorizontalView(
    context: Context,
    src: Int,
    textName: String,
    textInfo: String,
    textAchieves: String
) : ViewGroup(context) {


    private val img: AppCompatImageView
    private val tvName: TextView
    private val tvInfo: TextView
    private val tvAchieves: TextView

    private val indent: Int = 16.dp
    private val textIndent: Int = 8.dp

    private val gradient: GradientDrawable

    init{
        setPadding(indent)
        setWillNotDraw(false)

        img = AppCompatImageView(context).apply {
            setImageResource(src)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        addView(img)

        tvName = TextView(context).apply {
            text = textName
            textSizeDp = 20f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        addView(tvName)

        tvInfo = TextView(context).apply {
            text = textInfo
            textSizeDp = 15f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        addView(tvInfo)

        tvAchieves = TextView(context).apply {
            //изменяем
            text = textAchieves
            textSizeDp = 20f
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        addView(tvAchieves)

        gradient = DrawableUtil.createGradient(R.color.top_color_events,
            R.color.bottom_color_events, GradientDrawable.Orientation.TOP_BOTTOM).apply {
            cornerRadius = resources.getDimension(R.dimen.corner_icon_radius)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)

        val imgSize = 90.dp
        var spec = MeasureSpec.makeMeasureSpec(imgSize, MeasureSpec.EXACTLY)
        img.measure(spec, spec)

        val textSize = w - (paddingLeft + imgSize + indent + paddingRight)
        spec = MeasureSpec.makeMeasureSpec(textSize, MeasureSpec.AT_MOST)
        tvName.measure(spec, 0)
        tvInfo.measure(spec, 0)
        tvAchieves.measure(spec, 0)

        val h = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(w, h)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var l = paddingLeft
        var t = paddingTop
        img.layout(l, t, l + img.measuredWidth, t + img.measuredHeight)

        l = img.right + textIndent
        tvName.layout(l, t, l + tvName.measuredWidth, t + tvName.measuredHeight)

        t = tvName.bottom + textIndent
        l = img.right + textIndent + (tvInfo.measuredWidth  / 3.5).toInt()
        tvInfo.layout(l, t, l + tvInfo.measuredWidth, t + tvInfo.measuredHeight)

        l = right - (tvAchieves.measuredWidth + paddingRight)
        t = tvInfo.bottom + textIndent
        tvAchieves.layout(l, t, l + tvAchieves.measuredWidth, t + tvAchieves.measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {

        gradient.setBounds(0, 0, width, height)
        if (canvas != null) {
            gradient.draw(canvas)
        }
    }
}