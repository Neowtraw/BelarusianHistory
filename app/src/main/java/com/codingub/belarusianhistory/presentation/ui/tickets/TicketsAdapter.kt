package com.codingub.belarusianhistory.presentation.ui.tickets

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.TicketViewElementBinding
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.utils.TicketUtil

class TicketsAdapter(
    private var ticketList: List<Ticket>
) : RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder>(){

    private lateinit var binding: TicketViewElementBinding

    inner class TicketsViewHolder(private val binding: TicketViewElementBinding) : RecyclerView.ViewHolder(binding.root){

        private var granted = false

        fun binding(item: Ticket){
            binding.tvTicket.text = item.name
            binding.tvContent.text = TicketUtil.groupQuestions(item.questionList)
            binding.ivPassed.setImageResource(
                if(item.isPassed == 0){
                    R.drawable.passed.apply {

                    }
                } else{
                    R.drawable.not_passed
                }
            )
        }

        fun update(pos: Int){
            itemView.setOnClickListener{
                if(!granted) {
                    binding.flTicket.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
                    notifyItemChanged(pos)
                    granted = true
                } else{
                    binding.flTicket.layoutParams.height = 0
                    notifyItemChanged(pos)
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
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }
}