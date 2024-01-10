package com.codingub.belarusianhistory.ui.adapters.change

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.databinding.ItemChangeTqsBinding
import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.ui.fragments.change.ChangeAdapter
import com.codingub.belarusianhistory.utils.Font

data class ChangeTicketQuestionDto(
    val item: TicketQuestionDto,
    val deleteType: DeleteType
)

class ChangeTicketQuestionAdapter(
    val onSaveClicked: (ChangeTicketQuestionDto, Int) -> Unit,
    val onGoToSelected: (String) -> Unit
) : RecyclerView.Adapter<ChangeTicketQuestionAdapter.TicketQuestionsViewHolder>(),
    ChangeAdapter<ChangeTicketQuestionDto> {


    override var items: MutableList<ChangeTicketQuestionDto> = mutableListOf()
    override var selectedItems: MutableList<ChangeTicketQuestionDto> = mutableListOf()

    // for deleting items
    var isRemoving: Boolean = false

    // achieve animation
    private var isShowed: Boolean = false

    private lateinit var binding: ItemChangeTqsBinding

    // ticket questions
    inner class TicketQuestionsViewHolder(val binding: ItemChangeTqsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding() {
            with(binding) {
                // typeface
                tvTicketQuestion.typeface = Font.SEMIBOLD
                achieveHeader.tvAchieve.typeface = Font.SEMIBOLD
                achieve.etName.typeface = Font.REGULAR
                achieve.etDescription.typeface = Font.REGULAR

                // set
                etName.apply {
                    typeface = Font.REGULAR
                    setText(items[bindingAdapterPosition].item.name)
                }
                etDescription.apply {
                    typeface = Font.REGULAR
                    setText(items[bindingAdapterPosition].item.info)
                }
                items[bindingAdapterPosition].item.achieve?.let {
                    achieve.etName.setText(it.name)
                    achieve.etDescription.setText(it.info)
                }
            }

            setupListeners()
        }

        private fun setupListeners() {
            val tq = items[bindingAdapterPosition]

            with(binding) {
                etName.addTextChangedListener { s ->
                    tq.item.name = s.toString()
                    showSaveBtn()
                }
                etDescription.addTextChangedListener { s ->
                    tq.item.info = s.toString()
                    showSaveBtn()
                }
                achieve.etName.addTextChangedListener { s ->
                    updateAchieve(
                        s.toString(), tq.item.achieve?.info ?: ""
                    )
                }
                achieve.etDescription.addTextChangedListener { s ->
                    updateAchieve(
                        tq.item.achieve?.name ?: "", s.toString()
                    )
                }
                achieveHeader.llAchieve.setOnClickListener {
                    if (isRemoving.not()) {
                        isShowed = !isShowed

                        if (isShowed) ChangeAdaptersUtils.showAchieve(achieve.root)
                        else ChangeAdaptersUtils.hideAchieve(achieve.root)
                        ChangeAdaptersUtils.animateShowIcon(
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
            Aditional
        */

        private fun updateAchieve(name: String, info: String) {
            val tq = items[bindingAdapterPosition]
            tq.item.achieve =
                (tq.item.achieve ?: AchieveDto(
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
        Adapter Settings
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChangeTicketQuestionAdapter.TicketQuestionsViewHolder {
        binding = ItemChangeTqsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketQuestionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketQuestionsViewHolder, position: Int) {
        holder.binding()
    }

    override fun getItemCount(): Int = items.size

    /*
        Additional
     */

    override fun removeItems(locItems: List<ChangeTicketQuestionDto>) {
        val indicesToRemove = locItems.map { items.indexOf(it) }.sortedDescending()

        indicesToRemove.forEach { position ->
            if (position >= 0 && position < items.size) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    override fun addItem(item: ChangeTicketQuestionDto) {
        val existingIndex = items.indexOf(item)
        if (existingIndex == -1) {
            items.add(item)
            selectedItems.add(item)

            notifyItemInserted(items.size - 1)
        }
    }
}


