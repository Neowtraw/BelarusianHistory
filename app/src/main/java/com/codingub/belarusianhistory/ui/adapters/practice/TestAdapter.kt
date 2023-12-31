package com.codingub.belarusianhistory.ui.adapters.practice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.models.practices.Answer
import com.codingub.belarusianhistory.databinding.ItemTestBinding
import com.codingub.belarusianhistory.utils.Font

class TestAdapter(
    private val answerList: List<Answer>,
    private val onAnswerSelected: (String, String, Boolean) -> Unit
) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {
    private val selectedStates = BooleanArray(answerList.size) { false }
    private var correctAnswer: Answer? = null

    init {
        correctAnswer = answerList.find { it.isTrue }
    }

    inner class ViewHolder(private val binding: ItemTestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvAnswer.apply {
                typeface = Font.REGULAR
                text = answerList.getOrNull(bindingAdapterPosition)?.info

                setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        for (i in selectedStates.indices) {
                            selectedStates[i] = i == position
                        }
                        notifyDataSetChanged()

                        val answer = answerList.getOrNull(position)
                        if (answer?.isTrue == true) {
                            onAnswerSelected(answer.info, answer.info, true)
                        } else {
                            val correctAnswer = correctAnswer
                            if (correctAnswer != null) {
                                if (answer != null) {
                                    onAnswerSelected(answer.info, correctAnswer.info, false)
                                }
                            }
                        }
                    }
                }

            }
        }

        fun bind() {
            binding.tvAnswer.apply {
                typeface = Font.REGULAR
                text = answerList[bindingAdapterPosition].info
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTestBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        holder.itemView.isSelected = selectedStates[position]
    }


    override fun getItemCount(): Int = answerList.size
}