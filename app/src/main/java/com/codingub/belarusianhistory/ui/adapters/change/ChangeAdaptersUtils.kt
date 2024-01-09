package com.codingub.belarusianhistory.ui.adapters.change

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.utils.extension.dp

object ChangeAdaptersUtils {

    fun checkNullableValues(vararg data: String?): AddItemState<Unit> {
        data.forEach { item ->
            if (item.isNullOrBlank()) return AddItemState.Error(error = "No one item can be null")
        }
        return AddItemState.OK()
    }

    fun checkTimer(timer: String): AddItemState<Unit> {
        if (timer.length < 4) return AddItemState.Error(error = "Uncorrect type of time")
        return AddItemState.OK()
    }

    // animation
    fun animateShowIcon(view: View, isShowed: Boolean) {
        val fromDegree = if (isShowed) 0f else 180f
        val toDegree = if (isShowed) 180f else 0f

        RotateAnimation(
            fromDegree, toDegree,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 500L
            fillAfter = true
            view.startAnimation(this)
        }
    }

    fun showAchieve(achieve: View) {
        achieve.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
        achieve.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
        )
        val initialHeight = achieve.measuredHeight
        achieve.layoutParams.height = 0.dp

        ValueAnimator.ofInt(0.dp, initialHeight).apply {
            interpolator = AccelerateInterpolator()
            duration = 500L
            addUpdateListener {
                achieve.layoutParams.height = it.animatedValue as Int
                achieve.requestLayout()
            }
            start()
        }
    }

    fun hideAchieve(achieve: View) {
        val initialHeight = achieve.height

        ValueAnimator.ofInt(initialHeight, 0.dp).apply {
            interpolator = DecelerateInterpolator()
            duration = 500L
            addUpdateListener { valueAnimator ->
                val animatedValue = valueAnimator.animatedValue as Int
                achieve.layoutParams.height = animatedValue
                achieve.requestLayout()
            }
            start()
        }
    }

}

enum class DeleteType {
    LOCAL,
    REMOTE
}

// for checking empty values
sealed class AddItemState<T> {
    class OK<T> : AddItemState<T>()
    data class Error<T>(val error: String) : AddItemState<T>()
    data class Result<T>(val ticket: TicketDto) : AddItemState<T>()
}
