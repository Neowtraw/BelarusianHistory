package com.codingub.belarusianhistory.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.ui.custom.view.SelectedView
import com.codingub.belarusianhistory.sdk.ThemeType
import com.codingub.belarusianhistory.utils.Resource

class ThemeAdapter(
    private val themeList: List<ThemeType>,
    private val lastPosition: Int,
    private val onItemSelect: (ThemeType) -> Unit
) : RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {


    private var selectedPosition = lastPosition // Индекс выбранного элемента


    inner class  ThemeViewHolder(val view: SelectedView): RecyclerView.ViewHolder(view){

        init {
            view.setOnClickListener {

                if (bindingAdapterPosition != RecyclerView.NO_POSITION
                    && bindingAdapterPosition != selectedPosition) {

                    if (selectedPosition != -1) {
                        notifyItemChanged(selectedPosition)
                    }

                    selectedPosition = bindingAdapterPosition
                    view.setChecked(true, animated = true)

                    onItemSelect.invoke(themeList[bindingAdapterPosition])
                }
            }
        }

        fun bind(){
            view.text = Resource.string(themeList[bindingAdapterPosition].nameRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val view = SelectedView(parent.context)
        return ThemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
       holder.view.setChecked(position == selectedPosition, animated = false)
       holder.bind()
    }

    override fun getItemCount(): Int = themeList.size
}