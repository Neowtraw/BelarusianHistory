package com.codingub.belarusianhistory.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.databinding.MemberGroupItemBinding
import com.codingub.belarusianhistory.utils.Font

class GroupStatisticAdapter(
    private val users: List<String>,
    private inline val onMemberSelected: (String) -> Unit
) : RecyclerView.Adapter<GroupStatisticAdapter.ViewHolder>() {

    private lateinit var binding: MemberGroupItemBinding

    inner class ViewHolder(private val binding: MemberGroupItemBinding) : RecyclerView.ViewHolder(binding.root){
        internal fun bind(){
            binding.tvMember.typeface = Font.REGULAR
        }

        init {
            binding.deleteFromGroup.setOnClickListener {
                onMemberSelected(users[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = MemberGroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = users.size
}