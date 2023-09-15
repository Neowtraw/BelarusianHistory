package com.codingub.belarusianhistory.presentation.ui.achieves

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

            if(item.isPassed == 0){
                //binding.root.setBackgroundResource(Resource.color(R.color.achieve_not_passed))
            }

            binding.tvName.apply {
                text = item.name
                typeface = Font.SEMIBOLD
            }
            binding.tvInfo.apply {
                text = item.info
                typeface = Font.REGULAR
            }
            binding.ivPassed.setImageResource(
                if(item.isPassed == 0){
                    R.drawable.not_passed.apply {
                    }
                } else{
                    R.drawable.passed.apply {

                    }
                }
            )
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