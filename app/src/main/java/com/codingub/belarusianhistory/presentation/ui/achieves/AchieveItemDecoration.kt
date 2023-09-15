package com.codingub.belarusianhistory.presentation.ui.achieves

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.utils.extension.dp

class AchieveItemDecoration() : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = 9.dp
    }

}