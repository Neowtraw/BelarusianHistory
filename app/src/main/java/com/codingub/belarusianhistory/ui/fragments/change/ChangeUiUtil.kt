package com.codingub.belarusianhistory.ui.fragments.change

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun createItemDecoration(spacing: Int): RecyclerView.ItemDecoration {
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

interface ChangeAdapter<T> {
    var items: MutableList<T>
    var selectedItems: MutableList<T>

    fun removeItems(locItems: List<T>)
    fun addItem(item: T)
}

interface ChangeViewModel<Item, DState, IState> {
    fun deleteItems(items: Item)
    fun setDeleteState(state: DState)
    fun setInsertState(state: IState)
}