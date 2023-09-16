package com.codingub.belarusianhistory.presentation.ui.tickets_practice

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainItemDecorator(
    private val spacing: Int,
    private val spanCount: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        outRect.bottom = spacing

        if(position % spanCount == 0 && position != 0){
            outRect.top = spacing * 4
        }
    }

}