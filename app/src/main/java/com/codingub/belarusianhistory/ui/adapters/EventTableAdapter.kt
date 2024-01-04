package com.codingub.belarusianhistory.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.databinding.ItemEventTableBinding
import com.codingub.belarusianhistory.data.models.events.EventDto
import com.codingub.belarusianhistory.utils.Font

class EventTableAdapter() : RecyclerView.Adapter<EventTableAdapter.ViewHolder>() {

    private lateinit var binding: ItemEventTableBinding

    var events: List<EventDto>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<EventDto>() {
        override fun areItemsTheSame(oldItem: EventDto, newItem: EventDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventDto, newItem: EventDto): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)


    inner class ViewHolder(binding: ItemEventTableBinding) : RecyclerView.ViewHolder(binding.root) {

        internal fun bind() {
            binding.tvFirst.apply {
                typeface = Font.SEMIBOLD
                text = events[bindingAdapterPosition].title
            }
            binding.tvSecond.apply {
                typeface = Font.REGULAR
                text = events[bindingAdapterPosition].description
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemEventTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = events.size
}