package com.codingub.belarusianhistory.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.sdk.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.databinding.ItemTicketInfoBinding
import com.codingub.belarusianhistory.utils.Font

class TicketInfoAdapter : RecyclerView.Adapter<TicketInfoAdapter.TicketInfoViewHolder>() {

    var tickets: List<TicketQuestionDto>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<TicketQuestionDto>() {
        override fun areItemsTheSame(oldItem: TicketQuestionDto, newItem: TicketQuestionDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TicketQuestionDto, newItem: TicketQuestionDto): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    private lateinit var binding: ItemTicketInfoBinding

    inner class TicketInfoViewHolder(private val binding: ItemTicketInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
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
        binding = ItemTicketInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketInfoViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return tickets.size
    }
}