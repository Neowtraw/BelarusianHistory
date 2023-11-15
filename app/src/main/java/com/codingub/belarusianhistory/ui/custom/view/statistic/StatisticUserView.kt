package com.codingub.belarusianhistory.ui.custom.view.statistic

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.textSizeDp

/**
 * [StatisticUserView] shows all information about user
 * and his achievements.
 */
@SuppressLint("ViewConstructor")
class StatisticUserView(
    username: String,
    passedTickets: Int, // tickets
    allTickets: Int,
    passedPractice: Int, //practice
    allPractice: Int,
    passedAchieves: Int, //achieves
    allAchieves: Int,
    context: Context
) : ViewGroup(context) {

    private val tvUsername: TextView
    private val tvStatistic: TextView //statistic:
    private val profile: ImageView

    private val ticketsStatistic: StatisticItem
    private val practiceStatistic: StatisticItem
    private val achievesStatistic: StatisticItem

    private val changeProfile: TextView

    //background
    private val gradient: GradientDrawable


    init {
        setPadding(16.dp)
        setWillNotDraw(false)

        tvUsername = TextView(context).apply {
            typeface = Font.SEMIBOLD
            textSizeDp = 14f
            isSingleLine = true
            text = username
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(Resource.color(R.color.text_color))
            gravity = Gravity.CENTER
        }
        addView(tvUsername)

        tvStatistic = TextView(context).apply {
            typeface = Font.REGULAR
            textSizeDp = 14f
            isSingleLine = true
            text = Resource.string(R.string.statistic)
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(Resource.color(R.color.text_color))
            gravity = Gravity.START
        }
        addView(tvStatistic)

        profile = ImageView(context).apply {
            post {
                Glide.with(this)
                    .load(AssetUtil.imagesImageUri("profile"))
                    .fitCenter()
                    .circleCrop()
                    .into(this)
            }
        }
        addView(profile)

        ticketsStatistic = StatisticItem(
            context = context,
            title = Resource.string(R.string.tickets),
            firstValue = passedTickets,
            secondValue = allTickets
        ).apply {
            gravity = Gravity.START
        }
        addView(ticketsStatistic)

        practiceStatistic = StatisticItem(
            context = context,
            title = Resource.string(R.string.practice),
            firstValue = passedPractice,
            secondValue = allPractice
        ).apply {
            gravity = Gravity.START
        }
        addView(practiceStatistic)

        achievesStatistic = StatisticItem(
            context = context,
            title = Resource.string(R.string.achieves),
            firstValue = passedAchieves,
            secondValue = allAchieves
        ).apply {
            gravity = Gravity.START
        }
        addView(achievesStatistic)

        changeProfile = TextView(context).apply {
            typeface = Font.REGULAR
            textSizeDp = 14f
            isSingleLine = true
            text = Resource.string(R.string.change_profile)
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(Resource.color(R.color.add_color))
            gravity = Gravity.CENTER
        }
        addView(changeProfile)

        // background
        gradient = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            color = ColorStateList.valueOf(Resource.color(R.color.bg_btn))
            cornerRadius = resources.getDimension(R.dimen.corner_icon_radius)

        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var l = paddingLeft
        var t = paddingTop
        profile.layout(l, t, l + profile.measuredWidth, t + profile.measuredHeight)

        l = profile.right + 10.dp
        tvUsername.layout(l, t, l + tvUsername.measuredWidth, t + tvUsername.measuredHeight)

        t = tvUsername.bottom + 10.dp
        tvStatistic.layout(l, t, l + tvStatistic.measuredWidth, t + tvStatistic.measuredHeight)

        //statistic
        t = tvStatistic.bottom + 10.dp
        ticketsStatistic.layout(
            l,
            t,
            l + ticketsStatistic.measuredWidth,
            t + ticketsStatistic.measuredHeight
        )
        t = ticketsStatistic.bottom + 10.dp
        practiceStatistic.layout(
            l,
            t,
            l + practiceStatistic.measuredWidth,
            t + practiceStatistic.measuredHeight
        )
        t = practiceStatistic.bottom + 10.dp
        achievesStatistic.layout(
            l,
            t,
            l + achievesStatistic.measuredWidth,
            t + achievesStatistic.measuredHeight
        )

        l = paddingLeft
        t = achievesStatistic.bottom + 30.dp
        changeProfile.layout(
            l,
            t,
            l + changeProfile.measuredWidth,
            t + changeProfile.measuredHeight
        )

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)

        //image
        val imgSize = 100.dp
        var spec = MeasureSpec.makeMeasureSpec(imgSize, MeasureSpec.EXACTLY)
        profile.measure(spec, spec)


        val textSize = w - (paddingLeft + imgSize + paddingRight)
        spec = MeasureSpec.makeMeasureSpec(textSize, MeasureSpec.EXACTLY)

        //statistic
        tvUsername.measure(spec, 0)
        tvStatistic.measure(spec, 0)

        ticketsStatistic.measure(spec, 0)
        practiceStatistic.measure(spec, 0)
        achievesStatistic.measure(spec, 0)

        changeProfile.measure(MeasureSpec.makeMeasureSpec(w - paddingLeft, MeasureSpec.EXACTLY), 0)
        setMeasuredDimension(w, changeProfile.bottom + changeProfile.measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        gradient.setBounds(0, 0, width, height)
        gradient.draw(canvas)
    }
}