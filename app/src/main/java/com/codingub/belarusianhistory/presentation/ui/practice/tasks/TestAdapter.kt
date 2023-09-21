package com.codingub.belarusianhistory.presentation.ui.practice.tasks

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.databinding.TestItemBinding
import com.codingub.belarusianhistory.domain.model.Answer
import com.codingub.belarusianhistory.utils.Font

class TestAdapter(
   private val answerList: List<Answer>
) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: TestItemBinding) : RecyclerView.ViewHolder(binding.root){

        init{
        }

        fun bind(){
            binding.tvAnswer.apply {
                typeface = Font.EXTRABOLD
                text = answerList[bindingAdapterPosition].answerName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 4
}