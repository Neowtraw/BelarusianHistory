package com.codingub.belarusianhistory.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.databinding.ItemMemberGroupBinding
import com.codingub.belarusianhistory.utils.Font

class GroupStatisticAdapter(
    private inline val onMemberSelected: (String) -> Unit
) : RecyclerView.Adapter<GroupStatisticAdapter.ViewHolder>() {

    private lateinit var binding: ItemMemberGroupBinding

    var users: List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)


    inner class ViewHolder(private val binding: ItemMemberGroupBinding) : RecyclerView.ViewHolder(binding.root){
        internal fun bind(){
            binding.tvMember.typeface = Font.REGULAR
            binding.tvMember.text = users[bindingAdapterPosition]
        }

        init {
            binding.deleteFromGroup.setOnClickListener {
                onMemberSelected(users[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMemberGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = users.size
}