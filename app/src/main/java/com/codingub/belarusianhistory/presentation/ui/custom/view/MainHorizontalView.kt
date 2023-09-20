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
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.DrawableUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ImageUtil
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.textSizeDp

@SuppressLint("ViewConstructor")
class MainHorizontalView(
    context: Context,
    src: String,
    textName: String,
    textInfo: String,
    textAchieves: String
) : ViewGroup(context) {


    private val img: AppCompatImageView
    private val tvName: TextView
    private val tvAchieves: TextView

    private val indent: Int = 16.dp
    private val textIndent: Int = 8.dp

    private val gradient: GradientDrawable

    private var tvInfo: TextView

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

        img = AppCompatImageView(context).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        ImageUtil.load(AssetUtil.menuImageUri(src)){
            img.apply {
                setImageDrawable(it)
                this@MainHorizontalView.invalidate()
            }
        }
        addView(img)

        tvName = TextView(context).apply {
            text = textName
            textSizeDp = 17f
            gravity = Gravity.CENTER
            typeface = Font.EXTRABOLD
            setTextColor(ContextCompat.getColor(context, R.color.text_color))
        }
        addView(tvName)

        tvInfo = TextView(context).apply {
            text = textInfo
            textSizeDp = 15f
            gravity = Gravity.CENTER
            typeface = Font.LIGHT
            setTextColor(ContextCompat.getColor(context, R.color.text_color))
        }
        addView(tvInfo)

        tvAchieves = TextView(context).apply {
            //изменяем
            text = textAchieves
            textSizeDp = 20f
            gravity = Gravity.END
            typeface = Font.REGULAR
            setTextColor(ContextCompat.getColor(context, R.color.text_color))
        }
        addView(tvAchieves)

        gradient = DrawableUtil.createGradient(R.color.top_color_events,
            R.color.bottom_color_events, GradientDrawable.Orientation.TOP_BOTTOM).apply {
            cornerRadius = resources.getDimension(R.dimen.corner_icon_radius)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)

        val imgSize = 120.dp
        var spec = MeasureSpec.makeMeasureSpec(imgSize, MeasureSpec.EXACTLY)
        img.measure(spec, spec)

        val textSize = w - (paddingLeft + imgSize + indent + paddingRight)
        spec = MeasureSpec.makeMeasureSpec(textSize, MeasureSpec.EXACTLY)
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
        l = img.right + textIndent
        tvInfo.layout(l, t, l + tvInfo.measuredWidth, t + tvInfo.measuredHeight)

        l = right - (tvAchieves.measuredWidth + paddingRight*2)
        t = tvInfo.bottom + textIndent
        tvAchieves.layout(l, t, l + tvAchieves.measuredWidth, t + tvAchieves.measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {

        gradient.setBounds(0, 0, width, height)
        if (canvas != null) {
            gradient.draw(canvas)
        }
    }
}