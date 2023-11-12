package com.codingub.belarusianhistory.ui.custom.view.statistic

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.remote.network.models.userdata.Group


class GroupAdapter(
    private val groups: List<Group>
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    inner class ViewHolder(val view: StatisticGroupItem) : RecyclerView.ViewHolder(view){

        fun bind(){
            view.title = groups[bindingAdapterPosition].name
            view.participants = groups[bindingAdapterPosition].userIds.size.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = StatisticGroupItem(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = groups.size
}