package com.codingub.belarusianhistory.presentation.ui.custom.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.sdk.AchievesCategory
import com.codingub.belarusianhistory.utils.DrawableUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.asFloat
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.mixWith
import com.codingub.belarusianhistory.utils.extension.setPaddingDp
import com.codingub.belarusianhistory.utils.extension.textSizeDp


@SuppressLint("ViewConstructor")
class CategoryAchieveView(
    context: Context,
    val category: AchievesCategory
) : AppCompatTextView(context) {

    var isChecked: Boolean = false
        private set
    private var checkProgress: Float = isChecked.asFloat()
        set(value){
            field = value
            background = DrawableUtil.rect(
                color = colorBg.mixWith(colorBgChecked, ratio = value),
                corner = 24f.dp
            )
            setTextColor(colorText.mixWith(colorTextChecked, ratio = value))
        }

    private val colorBg: Int = Resource.color(R.color.bg_btn)
    private val colorBgChecked: Int = Resource.color(R.color.bg_btn_checked)
    private val colorText: Int = Resource.color(R.color.text_not_checked)
    private val colorTextChecked: Int = Resource.color(R.color.text_color)


    init {
        setPaddingDp(24, 0, 24, 0)
        typeface = Font.REGULAR
        textSizeDp = 20f
        isSingleLine = true
        ellipsize = TextUtils.TruncateAt.END
        gravity = Gravity.CENTER
        setText(category.nameRes)

        checkProgress = isChecked.asFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(32.dp, MeasureSpec.EXACTLY)
        )
    }

    fun setChecked(checked: Boolean, animated: Boolean) {
        typeface = if (checked) Font.SEMIBOLD else Font.REGULAR

        if (animated) {
            ObjectAnimator.ofFloat(this, "checkProgress", checkProgress, checked.asFloat()).apply {
                duration = 200
            }.also { it.start() }
        } else {
            checkProgress = checked.asFloat()
        }

        isChecked = checked
    }

}