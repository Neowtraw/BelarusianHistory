package com.codingub.belarusianhistory.presentation.ui.tickets_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.databinding.TicketInfoItemBinding
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.utils.Font

class TicketInfoAdapter(
    private var tqList: List<TicketQuestion>
) : RecyclerView.Adapter<TicketInfoAdapter.TicketInfoViewHolder>() {

    private lateinit var binding: TicketInfoItemBinding

    inner class TicketInfoViewHolder(private val binding: TicketInfoItemBinding)  : RecyclerView.ViewHolder(binding.root){

        fun bind(){
            binding.tvTicketName.apply {
                text = tqList[bindingAdapterPosition].name
                typeface = Font.SEMIBOLD
            }
            binding.tvTickeInfo.apply {
                text = tqList[bindingAdapterPosition].info
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
        return tqList.size
    }
}