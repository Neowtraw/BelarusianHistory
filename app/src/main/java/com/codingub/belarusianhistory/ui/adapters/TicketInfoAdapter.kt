package com.codingub.belarusianhistory.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.remote.network.models.tickets.TicketQuestion
import com.codingub.belarusianhistory.databinding.TicketInfoItemBinding
import com.codingub.belarusianhistory.utils.Font

class TicketInfoAdapter : RecyclerView.Adapter<TicketInfoAdapter.TicketInfoViewHolder>() {

    var tickets: List<TicketQuestion>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<TicketQuestion>(){
        override fun areItemsTheSame(oldItem: TicketQuestion, newItem: TicketQuestion): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TicketQuestion, newItem: TicketQuestion): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    private lateinit var binding: TicketInfoItemBinding

    inner class TicketInfoViewHolder(private val binding: TicketInfoItemBinding)  : RecyclerView.ViewHolder(binding.root){

        fun bind(){
            binding.tvTicketName.apply {
                text = tickets[bindingAdapterPosition].name
                typeface = Font.SEMIBOLD
            }
            binding.tvTickeInfo.apply {
                text = tickets[bindingAdapterPosition].info
                typeface = Font.REGULAR
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketInfoViewHolder {
        binding = TicketInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketInfoViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return tickets.size
    }
}