package com.codingub.belarusianhistory.ui.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.AchieveViewItemBinding
import com.codingub.belarusianhistory.domain.model.Achieves.Achieve
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource

class AchieveAdapter: RecyclerView.Adapter<AchieveAdapter.AchieveViewHolder>() {

    private lateinit var binding: AchieveViewItemBinding

    var achieves: List<Achieve>
       get() = differ.currentList
       set(value) = differ.submitList(value)

    private val diffCallback = object: DiffUtil.ItemCallback<Achieve>(){
        override fun areItemsTheSame(oldItem: Achieve, newItem: Achieve): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Achieve, newItem: Achieve): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    inner class AchieveViewHolder (private val binding: AchieveViewItemBinding)  : RecyclerView.ViewHolder(binding.root){

        fun bind(){
            val item = achieves[bindingAdapterPosition]

            binding.tvName.apply {
                text = item.name
                typeface = Font.SEMIBOLD
            }
            binding.tvInfo.apply {
                text = item.info
                typeface = Font.REGULAR
            }
            binding.ivPassed.apply {
                setImageResource(
                    if (item.isPassed == 0) R.drawable.not_passed
                    else R.drawable.passed)
                setColorFilter(
                    if (item.isPassed == 0) Resource.color(R.color.icon_color_not_passed)
                    else Resource.color(R.color.icon_color_passed)
                )
            }
            val itemBackground = binding.root.background as GradientDrawable
            if(item.isPassed == 0) itemBackground.setColor(Resource.color(R.color.achieve_not_passed))
            else itemBackground.setColor(Resource.color(R.color.bg_btn))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchieveViewHolder {
        binding = AchieveViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AchieveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchieveViewHolder, position: Int) {
        holder.bind()

    }

    override fun getItemCount(): Int {
        return  achieves.size
    }
}