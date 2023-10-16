package com.codingub.belarusianhistory.ui.auth.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.databinding.RoleItemBinding
import com.codingub.belarusianhistory.sdk.AccessLevel
import com.codingub.belarusianhistory.utils.Font

class RoleAdapter : RecyclerView.Adapter<RoleAdapter.RoleViewHolder>()  {

    private val roles = AccessLevel.values().toList()

    inner class RoleViewHolder(private val binding: RoleItemBinding) : RecyclerView.ViewHolder(binding.root){

        internal fun bind(){
            binding.tvRole.typeface = Font.REGULAR
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoleViewHolder {
        val binding = RoleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoleViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = roles.size
}