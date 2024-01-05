package com.codingub.belarusianhistory.ui.adapters.change

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.databinding.ItemChangeTicketsBinding
import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.ui.adapters.change.ChangeAdaptersUtils.animateShowIcon
import com.codingub.belarusianhistory.ui.adapters.change.ChangeAdaptersUtils.hideAchieve
import com.codingub.belarusianhistory.ui.adapters.change.ChangeAdaptersUtils.showAchieve
import com.codingub.belarusianhistory.utils.Font
import okhttp3.internal.notifyAll


class ChangeTicketAdapter(
    val onDeleteSelected: (TicketDto) -> Unit
) : RecyclerView.Adapter<ChangeTicketAdapter.TicketsViewHolder>() {

    var tickets: MutableList<TicketDto> = mutableListOf()


    var isRemoving: Boolean = false
    private var isShowed: Boolean = false

    private lateinit var ticketsBinding: ItemChangeTicketsBinding

    /*
        ViewHolder
     */
    inner class TicketsViewHolder(val binding: ItemChangeTicketsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun unselectItem() {
            Log.d("", "unselected ${bindingAdapterPosition} successfully")
            ticketsBinding.root.isSelected = false
            notifyDataSetChanged()
        }

        fun binding() {
            setupListeners()

            with(ticketsBinding) {
                // typeface
                tvTicket.typeface = Font.SEMIBOLD
                tvAchieve.typeface = Font.SEMIBOLD
                tvTimer.typeface = Font.REGULAR
                
                etTimer.typeface = Font.REGULAR
                achieve.etName.typeface = Font.REGULAR
                achieve.etDescription.typeface = Font.REGULAR

                // set data
                etName.typeface = Font.REGULAR
                etName.setText(tickets[bindingAdapterPosition].name)

                tickets[bindingAdapterPosition].achievement?.let {
                    achieve.etName.setText(it.name)
                    achieve.etDescription.setText(it.info)
                }
            }
        }

        private fun setupListeners() {
            with(ticketsBinding) {

                val ticket = tickets[bindingAdapterPosition]
                etName.addTextChangedListener { s ->
                    ticket.name = s.toString()
                }
                achieve.etName.addTextChangedListener { s ->
                    if (ticket.achievement == null) {
                        ticket.achievement =
                            AchieveDto(name = s.toString(), info = "", type = AchieveType.TICKET)
                        return@addTextChangedListener
                    }
                    ticket.achievement!!.name = s.toString()
                }
                achieve.etDescription.addTextChangedListener { s ->
                    if (ticket.achievement == null) {
                        ticket.achievement =
                            AchieveDto(name = "", info = s.toString(), type = AchieveType.TICKET)
                        return@addTextChangedListener
                    }
                    ticket.achievement!!.name = s.toString()
                }
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

                        tickets[bindingAdapterPosition].timer = etTimer.text.toString().toLong()
                    }
                }
                show.setOnClickListener {
                    isShowed = !isShowed

                    if (isShowed) showAchieve(achieve.root)
                    else hideAchieve(achieve.root)
                    animateShowIcon(
                        show,
                        isShowed
                    )
                }
                root.setOnClickListener {
                    if (isRemoving) {
                        root.isSelected = !root.isSelected
                        onDeleteSelected(ticket)
                    }
                }
            }
        }
    }

    /*
        Adapter settings
     */

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
}



