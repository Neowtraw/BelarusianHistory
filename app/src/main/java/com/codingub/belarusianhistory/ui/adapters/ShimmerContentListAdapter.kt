package com.codingub.belarusianhistory.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.databinding.ItemPeriodBinding
import com.codingub.belarusianhistory.databinding.ShimmerContentListBinding

/**
 * Used for showing shimmer content in almost all lists
 */
class ShimmerContentListAdapter :
    RecyclerView.Adapter<ShimmerContentListAdapter.ViewHolder>() {

    private lateinit var binding: ShimmerContentListBinding

    inner class ViewHolder(val binding: ShimmerContentListBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ShimmerContentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = Unit
    override fun getItemCount() = 10
}

class ShimmerMapTypeListAdapter : RecyclerView.Adapter<ShimmerMapTypeListAdapter.ViewHolder>() {
    private lateinit var binding: ItemPeriodBinding

    inner class ViewHolder(val binding: ItemPeriodBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemPeriodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = Unit
    override fun getItemCount() = 5
}
