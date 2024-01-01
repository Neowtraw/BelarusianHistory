package com.codingub.belarusianhistory.ui.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.ItemAchieveViewBinding
import com.codingub.belarusianhistory.ui.viewmodels.AchieveStateUi
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource

class AchieveAdapter : RecyclerView.Adapter<AchieveAdapter.AchieveViewHolder>() {

    private lateinit var binding: ItemAchieveViewBinding

    var achieves: List<AchieveStateUi>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<AchieveStateUi>() {
        override fun areItemsTheSame(oldItem: AchieveStateUi, newItem: AchieveStateUi): Boolean {
            return oldItem.achieve.id == newItem.achieve.id
        }

        override fun areContentsTheSame(oldItem: AchieveStateUi, newItem: AchieveStateUi): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    inner class AchieveViewHolder(private val binding: ItemAchieveViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val item = achieves[bindingAdapterPosition]

            binding.tvName.apply {
                text = item.achieve.name
                typeface = Font.SEMIBOLD
            }
            binding.tvInfo.apply {
                text = item.achieve.info
                typeface = Font.REGULAR
            }
            binding.ivPassed.apply {
                setImageResource(
                    if (item.isPassed) R.drawable.not_passed
                    else R.drawable.passed
                )
                setColorFilter(
                    if (!item.isPassed) Resource.color(R.color.icon_color_not_passed)
                    else Resource.color(R.color.icon_color_passed)
                )
            }
            val itemBackground = binding.root.background as GradientDrawable
            if (!item.isPassed) itemBackground.setColor(Resource.color(R.color.achieve_not_passed))
            else itemBackground.setColor(Resource.color(R.color.bg_btn))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchieveViewHolder {
        binding = ItemAchieveViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AchieveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchieveViewHolder, position: Int) {
        holder.bind()

    }

    override fun getItemCount(): Int {
        return achieves.size
    }
}