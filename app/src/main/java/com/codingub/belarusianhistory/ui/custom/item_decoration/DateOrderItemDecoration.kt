package com.codingub.belarusianhistory.ui.custom.item_decoration

import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.ui.MainActivity
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp

class DateOrderItemDecoration() : RecyclerView.ItemDecoration() {

    //получаем стрелку
    private val divider: Drawable =
        ContextCompat.getDrawable(MainActivity.getInstance(), R.drawable.arrow)!!.apply {
            colorFilter = PorterDuffColorFilter(Resource.color(R.color.contrast), PorterDuff.Mode.SRC_IN)
        }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val nextChild = parent.getChildAt(i + 1)

            val left = child.right
            val right = nextChild.left
            val top = child.top + child.translationY
            val bottom = child.bottom + child.translationY

            divider.let {
                val dividerHeight = 18.dp
                val dividerWidth = 12.dp

                // Calculate the bounds for the divider image
                val dividerLeft = left + (right - left - dividerWidth) / 2
                val dividerRight = dividerLeft + dividerWidth
                val dividerTop = top + (bottom - top - dividerHeight) / 2
                val dividerBottom = dividerTop + dividerHeight

                it.setBounds(dividerLeft, dividerTop.toInt(), dividerRight, dividerBottom.toInt())
                it.draw(c)
            }
        }
    }


}