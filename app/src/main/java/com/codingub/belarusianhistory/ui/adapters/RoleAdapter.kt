package com.codingub.belarusianhistory.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.RoleItemBinding
import com.codingub.belarusianhistory.sdk.AccessLevel
import com.codingub.belarusianhistory.utils.Font

class RoleAdapter(
    val onRoleSelected: (AccessLevel) -> Unit
) : RecyclerView.Adapter<RoleAdapter.RoleViewHolder>() {

    private val roles = AccessLevel.values().toList()
    private val roleImages = listOf(
        R.drawable.ic_launcher_icon,
        R.drawable.ic_launcher_icon,
        R.drawable.ic_launcher_icon
    )

    inner class RoleViewHolder(private val binding: RoleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init{
            binding.root.setOnClickListener {
                onRoleSelected.invoke(roles[bindingAdapterPosition])

            }
        }

        internal fun bind() {
            binding.tvRole.typeface = Font.REGULAR
            binding.imgRole.setImageResource(
                roleImages[bindingAdapterPosition]
            )
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