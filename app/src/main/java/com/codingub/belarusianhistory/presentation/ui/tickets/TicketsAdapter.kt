package com.codingub.belarusianhistory.presentation.ui.tickets

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.TicketViewElementBinding
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.utils.TicketUtil
import com.codingub.belarusianhistory.utils.extension.dp

class TicketsAdapter(
    private var ticketList: List<Ticket>
) : RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder>(){

    private lateinit var binding: TicketViewElementBinding
    private val TYPE_NORMAL = 0
    private val TYPE_THIRD_ITEM = 1

    inner class TicketsViewHolder(private val binding: TicketViewElementBinding) : RecyclerView.ViewHolder(binding.root){
        var granted = false
        fun binding(item: Ticket){
            binding.tvTicket.text = item.name
            binding.tvContent.text = TicketUtil.groupQuestions(item.questionList)
            binding.ivPassed.setImageResource(
                if(item.isPassed == 0){
                    R.drawable.passed.apply {

                    }
                } else{
                    R.drawable.not_passed.apply {

                    }
                }
            )
        }

        fun update(pos: Int){
            itemView.setOnClickListener{
                if(!granted) {
                    binding.flTicket.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
                    granted = true
                } else{
                    binding.flTicket.layoutParams.height = 0
                    granted = false
                }
                notifyItemChanged(pos)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder {
        binding = TicketViewElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) {
        holder.binding(ticketList[position])
        holder.update(position)

        if (getItemViewType(position) == TYPE_THIRD_ITEM) {
            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.setMargins(0, 0, 0, 20.dp) // Установите необходимые значения отступа
            holder.itemView.layoutParams = layoutParams
        }
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % 3 == 0) {
            TYPE_THIRD_ITEM
        } else {
            TYPE_NORMAL
        }
    }
}