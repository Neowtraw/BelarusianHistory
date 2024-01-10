package com.codingub.belarusianhistory.ui.adapters.change

import android.text.Editable
import android.text.TextWatcher
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
import com.codingub.belarusianhistory.ui.fragments.change.ChangeAdapter
import com.codingub.belarusianhistory.utils.Font


data class ChangeTicketDto(
    val item: TicketDto,
    val deleteType: DeleteType
)

class ChangeTicketAdapter(
    val onSaveClicked: (ChangeTicketDto, Int) -> Unit,
    val onGoToSelected: (String) -> Unit,
) : RecyclerView.Adapter<ChangeTicketAdapter.TicketsViewHolder>(), ChangeAdapter<ChangeTicketDto> {

    override var items: MutableList<ChangeTicketDto> = mutableListOf()
    override var selectedItems: MutableList<ChangeTicketDto> = mutableListOf()

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
                achieveHeader.tvAchieve.typeface = Font.SEMIBOLD
                tvTimer.typeface = Font.REGULAR
                action.btnGoToTqs.typeface = Font.REGULAR

                etMins.typeface = Font.REGULAR
                etSecs.typeface = Font.REGULAR
                achieve.etName.typeface = Font.REGULAR
                achieve.etDescription.typeface = Font.REGULAR

                // set data
                etName.typeface = Font.REGULAR
                etName.setText(items[bindingAdapterPosition].item.name)

                items[bindingAdapterPosition].item.achievement?.let {
                    achieve.etName.setText(it.name)
                    achieve.etDescription.setText(it.info)
                }
            }
        }

        private fun setupListeners() {
            with(ticketsBinding) {

                val ticket = items[bindingAdapterPosition]
                etName.addTextChangedListener { s ->
                    ticket.item.name = s.toString()
                    showSaveBtn()
                }
                achieve.etName.addTextChangedListener { s ->
                    updateAchieve(
                        s.toString(), ticket.item.achievement?.info ?: ""
                    )
                }
                achieve.etDescription.addTextChangedListener { s ->
                    updateAchieve(
                        ticket.item.achievement?.name ?: "", s.toString()
                    )
                }
                etMins.addTextChangedListener(minWatcher)
                etSecs.addTextChangedListener(minWatcher)

                achieveHeader.llAchieve.setOnClickListener {
                    if (isRemoving.not()) {
                        isShowed = !isShowed

                        if (isShowed) showAchieve(achieve.root)
                        else hideAchieve(achieve.root)
                        animateShowIcon(
                            achieveHeader.show,
                            isShowed
                        )
                    }
                }
                action.btnGoToTqs.setOnClickListener {
                    if (isRemoving.not())
                        onGoToSelected(items[bindingAdapterPosition].item.id)
                }
                action.btnAcceptChanges.setOnClickListener {
                    if (isRemoving.not())
                        onSaveClicked(items[bindingAdapterPosition], bindingAdapterPosition)
                    binding.action.btnAcceptChanges.visibility = View.INVISIBLE

                }
                root.setOnClickListener {
                    if (isRemoving) {
                        root.isSelected = !root.isSelected
                        selectedItems.add(items[bindingAdapterPosition])
                    }
                }
            }
        }

        /*
            Additional
         */

        private val minWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val minutes = binding.etMins.text.toString().toLongOrNull() ?: 0
                val secs = binding.etSecs.text.toString().toLongOrNull() ?: 0
                items[bindingAdapterPosition].item.timer = minutes * 60 + secs
                showSaveBtn()
            }
        }

        private fun updateAchieve(name: String, info: String) {
            val tq = items[bindingAdapterPosition]
            tq.item.achievement =
                (tq.item.achievement ?: AchieveDto(
                    name = name,
                    info = info,
                    type = AchieveType.PRACTICE,
                    ownerId = items[bindingAdapterPosition].item.id
                )).apply {
                    this.name = name
                    this.info = info
                }
            showSaveBtn()
        }


        private fun showSaveBtn() {
            if (binding.action.btnAcceptChanges.visibility != View.VISIBLE) {
                binding.action.btnAcceptChanges.visibility = View.VISIBLE
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
    override fun getItemCount(): Int = items.size

    /*
        Additional
     */

    override fun removeItems(locItems: List<ChangeTicketDto>) {
        val indicesToRemove = locItems.map { items.indexOf(it) }.sortedDescending()

        indicesToRemove.forEach { position ->
            if (position >= 0 && position < items.size) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    override fun addItem(item: ChangeTicketDto) {
        val existingIndex = items.indexOf(item)
        if (existingIndex == -1) {
            items.add(item)
            selectedItems.add(item)

            notifyItemInserted(items.size - 1)
        }
    }

}



