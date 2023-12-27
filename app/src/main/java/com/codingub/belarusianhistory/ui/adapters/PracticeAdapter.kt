package com.codingub.belarusianhistory.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.sdk.models.tickets.TicketQuestion
import com.codingub.belarusianhistory.databinding.ItemPracticeViewBinding
import com.codingub.belarusianhistory.utils.Font

class PracticeAdapter(
    private var practiceList: List<TicketQuestion>,
    private inline val onPracticeSelected: (TicketQuestion) -> Unit
) : RecyclerView.Adapter<PracticeAdapter.PracticeViewHolder>() {

    private lateinit var binding: ItemPracticeViewBinding

    inner class PracticeViewHolder(private val binding: ItemPracticeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onPracticeSelected(practiceList[bindingAdapterPosition])
            }
        }

        fun binding(item: TicketQuestion) {
            binding.tvPractice.apply {
                text = item.name
                typeface = Font.REGULAR
            }

            binding.ivPassed.apply {
//                setImageResource(
//                    if (item.isPassed == 0) R.drawable.not_passed
//                    else R.drawable.passed)
//                setColorFilter(
//                    if (item.isPassed == 0) Resource.color(R.color.icon_color_not_passed)
//                    else Resource.color(R.color.icon_color_passed)
//                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        binding =
            ItemPracticeViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PracticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PracticeViewHolder, position: Int) {
        holder.binding(practiceList[position])
    }

    override fun getItemCount(): Int {
        return practiceList.size
    }
}