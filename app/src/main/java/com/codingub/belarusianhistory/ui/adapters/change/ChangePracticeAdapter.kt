package com.codingub.belarusianhistory.ui.adapters.change

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.databinding.ItemChangePracticeBinding
import com.codingub.belarusianhistory.utils.Font

class ChangePracticeAdapter(
    val onDeleteSelected: () -> Unit
) : RecyclerView.Adapter<ChangePracticeAdapter.PracticeViewHolder>() {

    var practices: List<PracticeQuestion>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<PracticeQuestion>() {
        override fun areItemsTheSame(
            oldItem: PracticeQuestion,
            newItem: PracticeQuestion
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PracticeQuestion,
            newItem: PracticeQuestion
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    private lateinit var binding: ItemChangePracticeBinding

    // practice
    inner class PracticeViewHolder(binding: ItemChangePracticeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding() {
            with(binding) {
                etName.apply {
                    typeface = Font.REGULAR
                    setText(practices[bindingAdapterPosition].name)
                }
                etDescription.apply {
                    typeface = Font.REGULAR
                    setText(practices[bindingAdapterPosition].info)
                }
                etAnswer.apply {
                    typeface = Font.REGULAR
                    setText(practices[bindingAdapterPosition].answers[0].info)
                }
                tvPractice.typeface = Font.SEMIBOLD
            }
        }

        private fun setupListeners() {
            with(binding) {

            }
        }

    }

    /*
        Adapter Settings
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        binding = ItemChangePracticeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PracticeViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PracticeViewHolder, position: Int) {
        holder.binding()
    }
    override fun getItemCount(): Int = practices.size
}