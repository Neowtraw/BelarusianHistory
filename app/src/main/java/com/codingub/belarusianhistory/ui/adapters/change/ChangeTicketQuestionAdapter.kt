package com.codingub.belarusianhistory.ui.adapters.change

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.databinding.ItemChangePracticeBinding
import com.codingub.belarusianhistory.databinding.ItemChangeTicketsBinding
import com.codingub.belarusianhistory.databinding.ItemChangeTqsBinding
import com.codingub.belarusianhistory.utils.Font

class ChangeTicketQuestionAdapter(
    val onChangeClicked: (AddItemState<TicketQuestionDto>) -> Unit,
    val onDeleteSelected: () -> Unit
) : RecyclerView.Adapter<ChangeTicketQuestionAdapter.TicketQuestionsViewHolder>() {

    var tqs: List<TicketQuestionDto>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<TicketQuestionDto>() {
        override fun areItemsTheSame(
            oldItem: TicketQuestionDto,
            newItem: TicketQuestionDto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TicketQuestionDto,
            newItem: TicketQuestionDto
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    // for deleting items
    val isRemoving: Boolean = false

    private lateinit var binding: ItemChangeTqsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeTicketQuestionAdapter.TicketQuestionsViewHolder {
        binding = ItemChangeTqsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketQuestionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketQuestionsViewHolder, position: Int) {
        holder.binding()
    }

    override fun getItemCount(): Int = tqs.size

    // ticket questions
    inner class TicketQuestionsViewHolder(binding: ItemChangeTqsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            setupListeners()
        }

        fun binding() {
            with(binding) {
                tvTicketQuestion.typeface = Font.SEMIBOLD
                tvAchieve.typeface = Font.SEMIBOLD
                tvName.typeface = Font.REGULAR
                tvDescription.typeface = Font.REGULAR

                achieve.etName.typeface = Font.REGULAR
                achieve.etDescription.typeface = Font.REGULAR
            }
        }

        private fun setupListeners() {
            with(binding){
                show.setOnClickListener {

                }

                btnAction.setOnClickListener {

                }
            }
        }
    }
}


