package com.codingub.belarusianhistory.presentation.ui.custom.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.setPadding
import com.codingub.belarusianhistory.R
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
    private val imageView: ImageView

    init{
        setPadding(16.dp)
        orientation = VERTICAL

        imageView = ImageView(context).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
            setColorFilter(R.color.contrast)
            alpha = 0f
        }
        addView(imageView, LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ))

        textView = TextView(context).apply {
            typeface = Font.REGULAR
            textSizeDp = 20f.dp
            isSingleLine = true
            ellipsize = TextUtils.TruncateAt.END
            setLines(1)
            setTextColor(Resource.color(R.color.text_color))
        }
        addView(textView, LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ))
        ImageUtil.load(AssetUtil.menuImageUri("selected"), imageView)
    }

    override fun setEnabled(enabled: Boolean) {
        if(isEnabled != enabled)
            super.setEnabled(enabled)

        alpha = if (enabled) 1.0f else 0.5f
        isClickable = enabled
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(16.dp, MeasureSpec.EXACTLY))
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