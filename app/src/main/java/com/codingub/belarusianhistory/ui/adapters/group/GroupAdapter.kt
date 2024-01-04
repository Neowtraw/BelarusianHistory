package com.codingub.belarusianhistory.ui.adapters.group

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.models.userdata.GroupDto
import com.codingub.belarusianhistory.ui.custom.view.statistic.StatisticGroupItem


class GroupAdapter(
    private inline val onGroupSelected: (GroupDto) -> Unit
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    var groups: List<GroupDto>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<GroupDto>() {
        override fun areItemsTheSame(oldItem: GroupDto, newItem: GroupDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GroupDto, newItem: GroupDto): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(val view: StatisticGroupItem) : RecyclerView.ViewHolder(view) {

        fun bind() {
            view.title = groups[bindingAdapterPosition].name
            view.participants = groups[bindingAdapterPosition].users.size.toString()
        }

        init {
            view.setOnClickListener {
                onGroupSelected(groups[bindingAdapterPosition])
            }
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