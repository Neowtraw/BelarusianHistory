package com.codingub.belarusianhistory.ui.adapters.change

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.databinding.ItemChangePracticeBinding

class ChangePracticeAdapter(
    val onChangeClicked: (AddItemState<PracticeQuestion>) -> Unit,
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

    // practice
    inner class PracticeViewHolder(binding: ItemChangePracticeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding() {
            with(binding) {

            }
        }

        private fun setupListeners() {
            with(binding) {

            }
        }

    }

}