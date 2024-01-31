package com.codingub.belarusianhistory.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.models.map.MapTypeDto
import com.codingub.belarusianhistory.databinding.ItemPeriodBinding
import com.codingub.belarusianhistory.utils.Font

class TypeAdapter(
    val types: List<MapTypeDto>,
    val onPeriodSelected: (MapTypeDto) -> Unit,
    ) : RecyclerView.Adapter<TypeAdapter.ViewHolder>() {

    private lateinit var binding: ItemPeriodBinding

    inner class ViewHolder(private val binding: ItemPeriodBinding) : RecyclerView.ViewHolder(binding.root) {

        init{
            binding.root.setOnClickListener{
                onPeriodSelected(types[bindingAdapterPosition])
            }
        }

        internal fun binding(context: Context) {
            binding.tvTitle.apply {
                typeface = Font.SEMIBOLD
                text = types[bindingAdapterPosition].title
            }
            binding.tvDescription.apply {
                typeface = Font.REGULAR
                text = types[bindingAdapterPosition].description
            }
            Glide.with(context)
                .load(types[bindingAdapterPosition].image)
                .override(4000, 3000)
               // .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(binding.imgType)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPeriodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(holder.itemView.context)

    }

    override fun getItemCount() = types.size
}