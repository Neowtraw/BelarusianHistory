package com.codingub.belarusianhistory.ui.adapters.change

import android.view.LayoutInflater
import android.view.View
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


data class ChangeTicketDto(
    val item: TicketDto,
    val deleteType: DeleteType
)

class ChangeTicketAdapter(
    val onSaveClicked: (ChangeTicketDto, Int) -> Unit,
    val onGoToSelected: (String) -> Unit,
) : RecyclerView.Adapter<ChangeTicketAdapter.TicketsViewHolder>() {

    var tickets: MutableList<ChangeTicketDto> = mutableListOf()
    var selectedTickets: MutableList<ChangeTicketDto> = mutableListOf()

    var isRemoving: Boolean = false
    private var isShowed: Boolean = false

    private lateinit var ticketsBinding: ItemChangeTicketsBinding

    /*
        ViewHolder
     */
    inner class TicketsViewHolder(val binding: ItemChangeTicketsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding() {
            setupListeners()

            with(ticketsBinding) {
                // typeface
                tvTicket.typeface = Font.SEMIBOLD
                tvAchieve.typeface = Font.SEMIBOLD
                tvTimer.typeface = Font.REGULAR
                btnGoToTqs.typeface = Font.REGULAR

                etMins.typeface = Font.REGULAR
                etSecs.typeface = Font.REGULAR
                achieve.etName.typeface = Font.REGULAR
                achieve.etDescription.typeface = Font.REGULAR

                // set data
                etName.typeface = Font.REGULAR
                etName.setText(tickets[bindingAdapterPosition].item.name)

                tickets[bindingAdapterPosition].item.achievement?.let {
                    achieve.etName.setText(it.name)
                    achieve.etDescription.setText(it.info)
                }
            }
        }

        private fun setupListeners() {
            with(ticketsBinding) {

                val ticket = tickets[bindingAdapterPosition]
                etName.addTextChangedListener { s ->
                    ticket.item.name = s.toString()
                    showSaveBtn()
                }
                achieve.etName.addTextChangedListener { s ->
                    if (ticket.item.achievement == null) {
                        ticket.item.achievement =
                            AchieveDto(name = s.toString(), info = "", type = AchieveType.TICKET)
                        return@addTextChangedListener
                    }
                    ticket.item.achievement!!.name = s.toString()
                    showSaveBtn()
                }
                achieve.etDescription.addTextChangedListener { s ->
                    if (ticket.item.achievement == null) {
                        ticket.item.achievement =
                            AchieveDto(name = "", info = s.toString(), type = AchieveType.TICKET)
                        return@addTextChangedListener
                    }
                    ticket.item.achievement!!.name = s.toString()
                    showSaveBtn()
                }
                etMins.addTextChangedListener {
                    val mins =
                        etMins.text.toString().toLongOrNull() ?: return@addTextChangedListener
                    val secs =
                        etSecs.text.toString().toLongOrNull() ?: return@addTextChangedListener

                    tickets[bindingAdapterPosition].item.timer = mins * 60 + secs
                    showSaveBtn()
                }
                etMins.addTextChangedListener {
                    val mins =
                        etMins.text.toString().toLongOrNull() ?: return@addTextChangedListener
                    val secs =
                        etSecs.text.toString().toLongOrNull() ?: return@addTextChangedListener

                    tickets[bindingAdapterPosition].item.timer = mins * 60 + secs
                    showSaveBtn()
                }
                llAchieve.setOnClickListener {
                    if (isRemoving.not()) {
                        isShowed = !isShowed

                        if (isShowed) showAchieve(achieve.root)
                        else hideAchieve(achieve.root)
                        animateShowIcon(
                            show,
                            isShowed
                        )
                    }
                }
                btnGoToTqs.setOnClickListener {
                    if (isRemoving.not())
                        onGoToSelected(tickets[bindingAdapterPosition].item.id)
                }
                btnAcceptChanges.setOnClickListener {
                    if (isRemoving.not())
                        onSaveClicked(tickets[bindingAdapterPosition], bindingAdapterPosition)
                    binding.btnAcceptChanges.visibility = View.INVISIBLE

                }
                root.setOnClickListener {
                    if (isRemoving) {
                        root.isSelected = !root.isSelected
                        selectedTickets.add(tickets[bindingAdapterPosition])
                    }
                }
            }
        }

        private fun showSaveBtn() {
            binding.btnAcceptChanges.visibility = View.VISIBLE
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

    /*
        Additional
     */


    fun removeItems(localTickets: List<ChangeTicketDto>) {
        val positionsToRemove = mutableListOf<Int>()

        localTickets.forEach { ticket ->
            val index = tickets.indexOf(ticket)
            if (index != -1) {
                positionsToRemove.add(index)
            }
        }

        val uniqueSortedPositions = positionsToRemove.distinct().sortedDescending()

        uniqueSortedPositions.forEach { position ->
            if (position < tickets.size && position < selectedTickets.size) {
                tickets.removeAt(position)
                selectedTickets.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    fun addItem(ticket: ChangeTicketDto) {
        val existingIndex = tickets.indexOf(ticket)
        if (existingIndex == -1) {
            tickets.add(ticket)
            selectedTickets.add(ticket)

            notifyItemInserted(tickets.size - 1)
        }
    }

}



