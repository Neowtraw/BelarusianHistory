package com.codingub.belarusianhistory.ui.practice.tasks.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.databinding.TestItemBinding
import com.codingub.belarusianhistory.domain.model.Answer
import com.codingub.belarusianhistory.utils.Font

class TestAdapter(
    private val answerList: List<Answer>
) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private lateinit var binding: TestItemBinding

    inner class ViewHolder(private val binding: TestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (answerList[bindingAdapterPosition].isTrue == 1) {
                    //код анимации или простого перекрашивания кнопки
                } else { //точно такая же логика
                }

            }
        }

        fun bind() {
            binding.tvAnswer.apply {
                typeface = Font.REGULAR
                text = answerList[bindingAdapterPosition].answerName
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = TestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = answerList.size
}