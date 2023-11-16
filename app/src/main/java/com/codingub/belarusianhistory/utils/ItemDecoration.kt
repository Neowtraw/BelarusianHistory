package com.codingub.belarusianhistory.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

object ItemDecoration {

    fun createLinBottomItemDecoration(spacing: Int): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = spacing
            }

        }
    }
}