package com.codingub.belarusianhistory.presentation.ui.settings

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.presentation.ui.custom.view.SelectedView

class ThemeAdapter(
    val themeList: List<Int>,
    val onThemeSelected: (Int) -> Unit
) : RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {


    inner class  ThemeViewHolder(val view: SelectedView): RecyclerView.ViewHolder(view){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val view = SelectedView(parent.context)
        return ThemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = themeList.size
}