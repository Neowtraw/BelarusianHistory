package com.codingub.belarusianhistory.ui.custom.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.setPadding
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.sdk.ThemeType
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ImageUtil
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.asFloat
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.textSizeDp


@SuppressLint("ViewConstructor")
class SelectedView(context : Context) :  LinearLayoutCompat(context){

    private val textView: TextView
    var text: String
        get() = textView.text.toString()
        set(value) {
            textView.text = value
        }

    private val imageView: ImageView

    var theme: ThemeType = ThemeType.DEFAULT
        set(value){
            field = value
        }

    init{
        setPadding(16.dp)
        orientation = HORIZONTAL

        imageView = ImageView(context).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
            setColorFilter(R.color.contrast)
            alpha = 0f
            gravity = Gravity.CENTER
            setColorFilter(Resource.color(R.color.contrast))
        }
        ImageUtil.load(AssetUtil.imagesImageUri("icon"), imageView)

        addView(imageView, LayoutParams(
            17.dp,
            17.dp
        ))

        textView = TextView(context).apply {
            typeface = Font.REGULAR
            textSizeDp = 14f
            isSingleLine = true
            text = Resource.string(theme.nameRes)
            ellipsize = TextUtils.TruncateAt.END
            setLines(1)
            setTextColor(Resource.color(R.color.text_color))
        }
        addView(textView, LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )).apply {
        }
    }

    fun setChecked(checked: Boolean, animated: Boolean){
        if(animated){
            ObjectAnimator.ofFloat(imageView, "alpha", imageView.alpha, checked.asFloat()).apply {
                duration = 500
            }.also { it.start() }
        } else{
            imageView.alpha = checked.asFloat()
        }
    }

}