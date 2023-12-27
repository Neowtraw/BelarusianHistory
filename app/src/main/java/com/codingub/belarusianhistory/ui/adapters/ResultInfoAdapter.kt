package com.codingub.belarusianhistory.ui.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.ItemResultInfoBinding
import com.codingub.belarusianhistory.sdk.UserPracticeAnswer
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp

class ResultInfoAdapter : RecyclerView.Adapter<ResultInfoAdapter.ViewHolder>() {

    private lateinit var binding: ItemResultInfoBinding

    var results: List<UserPracticeAnswer>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<UserPracticeAnswer>() {
        override fun areItemsTheSame(
            oldItem: UserPracticeAnswer,
            newItem: UserPracticeAnswer
        ): Boolean {
            return oldItem.pqInfo == newItem.pqInfo
        }

        override fun areContentsTheSame(
            oldItem: UserPracticeAnswer,
            newItem: UserPracticeAnswer
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)


    inner class ViewHolder(binding: ItemResultInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val item = results[bindingAdapterPosition]

            binding.tvHeader.apply {
                text = "${(bindingAdapterPosition + 1)}. ${item.pqInfo}"
                typeface = Font.EXTRABOLD
            }
            binding.tvRightAnswer.apply {
                text = item.rightAnswer.joinToString(", ")
                typeface = Font.REGULAR
            }
            binding.tvUserAnswer.apply {
                text = item.userAnswer.joinToString(", ")
                typeface = Font.REGULAR
            }

            val itemBackground = binding.root.background as GradientDrawable
            if (item.isRight) itemBackground.apply {
                setColor(Resource.color(R.color.is_right))
                setStroke(2.dp, Resource.color(R.color.is_right_stroke))
            }
            else itemBackground.apply {
                setColor(Resource.color(R.color.is_not_right))
                setStroke(2.dp, Resource.color(R.color.is_not_right_stroke))
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemResultInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = results.size
}