package com.codingub.belarusianhistory.ui.adapters.change

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.databinding.ItemChangeTicketsBinding
import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.ui.adapters.change.ChangeAdaptersUtils.animateShowIcon
import com.codingub.belarusianhistory.ui.adapters.change.ChangeAdaptersUtils.checkNullableValues
import com.codingub.belarusianhistory.ui.adapters.change.ChangeAdaptersUtils.checkTimer
import com.codingub.belarusianhistory.ui.adapters.change.ChangeAdaptersUtils.hideAchieve
import com.codingub.belarusianhistory.ui.adapters.change.ChangeAdaptersUtils.showAchieve
import com.codingub.belarusianhistory.utils.Font


class ChangeTicketAdapter(
    val onChangeClicked: (AddItemState<TicketDto>) -> Unit,
    val onDeleteSelected: (TicketDto) -> Unit
) : RecyclerView.Adapter<ChangeTicketAdapter.TicketsViewHolder>() {

    var tickets: List<TicketDto>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<TicketDto>() {
        override fun areItemsTheSame(
            oldItem: TicketDto,
            newItem: TicketDto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TicketDto,
            newItem: TicketDto
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    // for deleting items
    var isRemoving: Boolean = false

    private lateinit var ticketsBinding: ItemChangeTicketsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder {
        ticketsBinding = ItemChangeTicketsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketsViewHolder(ticketsBinding)
    }

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) = holder.binding()

    override fun getItemCount(): Int = tickets.size

    // tickets
    inner class TicketsViewHolder(binding: ItemChangeTicketsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // achieve animation
        var isShowed: Boolean = false

        init {
            setupListeners()
        }

        fun binding() {
            with(ticketsBinding) {
                tvTicket.typeface = Font.SEMIBOLD
                tvAchieve.typeface = Font.SEMIBOLD
                etName.typeface = Font.REGULAR

                achieve.etName.typeface = Font.REGULAR
                achieve.etDescription.typeface = Font.REGULAR

            }
        }

        private fun setupListeners() {
            with(ticketsBinding) {

                etName.addTextChangedListener { if (!btnAction.isVisible) btnAction.isVisible = true }
                etTimer.addTextChangedListener { s ->

                    s?.let {
                        val userInput = it.toString().replace("[^\\d]".toRegex(), "")
                        val formatted = buildString {
                            userInput.take(4).forEachIndexed { index, char ->
                                append(char)
                                if (index == 1) {
                                    append(" : ")
                                }
                            }
                        }
                        if (formatted != it.toString()) {
                            etTimer.setText(formatted)
                            etTimer.setSelection(formatted.length)
                        }
                        if (! btnAction.isVisible) btnAction.isVisible = true
                    }
                }


                show.setOnClickListener {
                    if (! btnAction.isVisible && isShowed)  btnAction.isVisible = true
                    require(isRemoving.not()) {
                        with(achieve) {
                            if (etName.text.isNullOrBlank().not()) etName.text = null
                            if (etDescription.text.isNullOrBlank()
                                    .not()
                            ) etDescription.text = null

                            isShowed = !isShowed
                            if (isShowed) showAchieve(
                                root
                            ) else hideAchieve(
                                root
                            )
                            animateShowIcon(
                                show,
                                isShowed
                            )
                        }
                    }
                }
                root.setOnClickListener {
                    if (isRemoving) {
                        root.isSelected = !root.isSelected
                        onDeleteSelected(tickets[bindingAdapterPosition])
                    }
                }

                btnAction.setOnClickListener {
                    require(isRemoving.not()) {

                        // check nullable values
                        val checking = (
                                if (isShowed) checkNullableValues(
                                    etName.text.toString(),
                                    achieve.etName.text.toString(),
                                    achieve.etDescription.text.toString()
                                )
                                else checkNullableValues(etName.text.toString())
                                ).also {
                                if (it is AddItemState.OK) checkTimer(etTimer.text.toString())
                            }

                        if (checking is AddItemState.OK) {
                            with(ticketsBinding) {
                                val ticket = TicketDto(
                                    name = etName.text.toString(),
                                    timer = etTimer.text.toString().toLong(),
                                    tqs = emptyList(),
                                    achievement = if (isShowed) AchieveDto(
                                        name = achieve.etName.text.toString(),
                                        info = achieve.etDescription.text.toString(),
                                        type = AchieveType.TICKET
                                    )
                                    else null
                                )
                                onChangeClicked(AddItemState.Result(ticket))
                            }
                        } else onChangeClicked(AddItemState.Error(""))
                    }
                }
            }
        }
    }
}



