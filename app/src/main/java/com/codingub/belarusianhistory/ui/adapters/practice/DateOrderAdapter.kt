package com.codingub.belarusianhistory.ui.adapters.practice

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.models.practices.Answer
import com.codingub.belarusianhistory.databinding.ItemDateOrderBinding
import com.codingub.belarusianhistory.utils.Font

class DateOrderAdapter(
    private val answerList: List<Answer>)
    : RecyclerView.Adapter<DateOrderAdapter.ViewHolder>() {

    private lateinit var binding: ItemDateOrderBinding
    private val userAnswers: MutableList<String> = mutableListOf()

    inner class ViewHolder(binding: ItemDateOrderBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(){
            binding.edInput.apply {
                typeface = Font.EXTRABOLD

                addTextChangedListener(object : TextWatcher{

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        val input = s.toString()

                        if(input == "") return
                        else userAnswers[bindingAdapterPosition] = input
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int) = Unit
                    override fun afterTextChanged(s: Editable?) = Unit
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemDateOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        userAnswers.add("")

        holder.bind()
    }

    override fun getItemCount(): Int = answerList.size

    fun getUserAnswers(): List<String>? {
        return if(userAnswers.any { it.isEmpty()}) null
        else userAnswers
    }
}