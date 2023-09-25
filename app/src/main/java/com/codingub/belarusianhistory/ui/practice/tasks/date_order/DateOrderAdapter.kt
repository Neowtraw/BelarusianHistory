package com.codingub.belarusianhistory.ui.practice.tasks.date_order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.databinding.DateOrderItemBinding
import com.codingub.belarusianhistory.domain.model.Answer
import com.codingub.belarusianhistory.utils.Font

class DateOrderAdapter(
    private val answerList: List<Answer>)
    : RecyclerView.Adapter<DateOrderAdapter.ViewHolder>() {

    private lateinit var binding: DateOrderItemBinding


    inner class ViewHolder(binding: DateOrderItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(){

            binding.edInput.apply {
                typeface = Font.EXTRABOLD
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DateOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = answerList.size
}