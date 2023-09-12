package com.codingub.belarusianhistory.presentation.ui.tickets_practice

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.TicketViewElementBinding
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.TicketUtil
import com.codingub.belarusianhistory.utils.extension.dp

class TicketAdapter(
    private var ticketList: List<Ticket>
) : RecyclerView.Adapter<TicketAdapter.TicketsViewHolder>(){

    private lateinit var binding: TicketViewElementBinding


    inner class TicketsViewHolder(private val binding: TicketViewElementBinding) : RecyclerView.ViewHolder(binding.root){
        var granted = false
        private var initialHeight = 0
        private var maxHeight = 0

        init {
            itemView.setOnClickListener {
                if (!granted) {
                    // Expand the view
                    val layoutParams = binding.flTicket.layoutParams
                    layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
                    binding.flTicket.layoutParams = layoutParams
                    binding.flTicket.post {
                        maxHeight = binding.flTicket.height
                        initialHeight = binding.flTicket.height
                        ValueAnimator.ofInt(0, maxHeight).apply {
                            addUpdateListener {
                                binding.flTicket.layoutParams.height = it.animatedValue as Int
                                binding.flTicket.requestLayout()
                            }
                            start()
                        }
                    }
                    granted = true
                } else {
                    // Collapse the view
                    ValueAnimator.ofInt(maxHeight, 0).apply {
                        addUpdateListener {
                            binding.flTicket.layoutParams.height = it.animatedValue as Int
                            binding.flTicket.requestLayout()
                        }
                        start()
                    }
                    granted = false
                }
            }
        }

        fun binding(item: Ticket){
            binding.tvTicket.apply {
                text = item.name
                typeface = Font.REGULAR
            }
            binding.tvContent.apply {
                text = TicketUtil.groupQuestions(item.questionList)
                typeface = Font.REGULAR
            }
            binding.ivPassed.setImageResource(
                if(item.isPassed == 0){
                    R.drawable.passed.apply {

                    }
                } else{
                    R.drawable.not_passed.apply {

                    }
                }
            )

            binding.flTicket.layoutParams.height = if (granted) FrameLayout.LayoutParams.WRAP_CONTENT else 0
            binding.flTicket.requestLayout()
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder {
        binding = TicketViewElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) {
        holder.binding(ticketList[position])
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

}