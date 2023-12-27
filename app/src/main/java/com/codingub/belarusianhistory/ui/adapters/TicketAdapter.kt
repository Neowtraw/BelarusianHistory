package com.codingub.belarusianhistory.ui.adapters

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.sdk.models.tickets.Ticket
import com.codingub.belarusianhistory.databinding.ItemTicketViewBinding
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.TicketUtil

class TicketAdapter(
    private var ticketList: List<Ticket>,
    private inline val onTicketSelected: (Ticket) -> Unit
) : RecyclerView.Adapter<TicketAdapter.TicketsViewHolder>() {

    private lateinit var binding: ItemTicketViewBinding


    inner class TicketsViewHolder(private val binding: ItemTicketViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var granted = false
        private var initialHeight = 0
        private var maxHeight = 0

        init {
            //анимация
            itemView.setOnClickListener {
                if (!granted) {
                    // Выставляем начальную высоту
                    val layoutParams = binding.flTicket.layoutParams
                    layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
                    binding.flTicket.layoutParams = layoutParams
                    binding.flTicket.measure(
                        View.MeasureSpec.UNSPECIFIED,
                        View.MeasureSpec.UNSPECIFIED
                    )
                    initialHeight = binding.flTicket.measuredHeight
                    layoutParams.height = 0
                    binding.flTicket.layoutParams = layoutParams

                    // Разворачиваем элемент
                    ValueAnimator.ofInt(0, initialHeight).apply {
                        interpolator = AccelerateInterpolator()
                        duration = 500
                        addUpdateListener {
                            binding.flTicket.layoutParams.height = it.animatedValue as Int
                            binding.flTicket.requestLayout()
                        }
                        start()
                    }
                    granted = true
                } else {
                    // Сворачиваем элемент
                    if (maxHeight == 0) {
                        maxHeight = binding.flTicket.height
                    }
                    ValueAnimator.ofInt(maxHeight, 0).apply {
                        interpolator = DecelerateInterpolator()
                        duration = 500
                        addUpdateListener {
                            binding.flTicket.layoutParams.height = it.animatedValue as Int
                            binding.flTicket.requestLayout()
                        }
                        start()
                    }
                    granted = false
                }
            }

            //переход на другой фрагмент
            binding.btnGoTo.setOnClickListener {
                onTicketSelected(ticketList[bindingAdapterPosition])
            }
        }

        fun binding(item: Ticket) {
            binding.btnGoTo.apply {
                typeface = Font.SEMIBOLD
            }
            binding.tvTicket.apply {
                text = item.name
                typeface = Font.REGULAR
            }
            binding.tvContent.apply {
                text = TicketUtil.groupQuestions(item.tqs)
                typeface = Font.REGULAR
            }
            binding.ivPassed.apply {
//                setImageResource(
//                    if (item.isPassed == 0) R.drawable.not_passed
//                    else R.drawable.passed)
//                setColorFilter(
//                    if (item.isPassed == 0) Resource.color(R.color.icon_color_not_passed)
//                    else Resource.color(R.color.icon_color_passed)
//                )
            }

            binding.flTicket.layoutParams.height =
                if (granted) FrameLayout.LayoutParams.WRAP_CONTENT else 0
            binding.flTicket.requestLayout()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder {
        binding =
            ItemTicketViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) {
        holder.binding(ticketList[position])
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

}