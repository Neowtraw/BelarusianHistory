package com.codingub.belarusianhistory.presentation.ui.tickets_practice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.TicketViewElementBinding
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.TicketUtil

class PracticeAdapter(
    private var practiceList: List<TicketQuestion>
) : RecyclerView.Adapter<PracticeAdapter.PracticeViewHolder>() {

    private lateinit var binding: TicketViewElementBinding

    inner class PracticeViewHolder(private val binding: TicketViewElementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(item: TicketQuestion){
            binding.tvTicket.apply {
                text = item.name
                typeface = Font.REGULAR
            }

            binding.ivPassed.setImageResource(
                if(item.isPassed == 0){
                    R.drawable.passed
                } else{
                    R.drawable.not_passed
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        binding = TicketViewElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PracticeViewHolder(binding)    }

    override fun onBindViewHolder(holder: PracticeViewHolder, position: Int) {
        holder.binding(practiceList[position])
    }

    override fun getItemCount(): Int {
        return practiceList.size
    }
}