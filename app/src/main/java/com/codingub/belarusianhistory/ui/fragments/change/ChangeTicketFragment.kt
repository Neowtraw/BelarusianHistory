package com.codingub.belarusianhistory.ui.fragments.change

import android.graphics.Rect
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentChangeItemsBinding
import com.codingub.belarusianhistory.ui.adapters.change.ChangeTicketAdapter
import com.codingub.belarusianhistory.ui.adapters.change.ChangeTicketDto
import com.codingub.belarusianhistory.ui.adapters.change.DeleteType
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.viewmodels.change.ChangeTicketViewModel
import com.codingub.belarusianhistory.ui.viewmodels.change.DeletedUiState
import com.codingub.belarusianhistory.ui.viewmodels.change.InsertedUiState
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangeTicketFragment : BaseFragment() {

    private lateinit var binding: FragmentChangeItemsBinding
    private var ticketsAdapter: ChangeTicketAdapter? = null

    private val vm by viewModels<ChangeTicketViewModel>()

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentChangeItemsBinding.inflate(inf, con, false)

        createTexts()
        createTicketList()
        selectListeners()

        observeChanges()
        return binding.root
    }

    private fun createTexts() {
        with(binding) {
            tvHeader.typeface = Font.EXTRABOLD
            tvHeader.text = Resource.string(R.string.tickets)
        }
    }


    private fun createTicketList() {
        binding.rvContent.apply {
            ticketsAdapter = ChangeTicketAdapter(
                onSaveClicked = { ticket, position ->
                    vm.saveChanges(ticket.item)
                },
                onGoToSelected = {
                    pushFragment(ChangeTqFragment(), "change_tq")
                })
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ticketsAdapter
            addItemDecoration(createItemDecoration(10.dp))
        }
    }

    private fun selectListeners() {
        with(binding) {

            // plus
            plus.setOnClickListener {
                addNewElement()
            }

            // delete
            delete.setOnClickListener {
                vm.isRemoved = !vm.isRemoved.also { bool ->
                    if (bool) unselectAllElements()
                }
                ticketsAdapter?.isRemoving = vm.isRemoved
                delete.isSelected = !delete.isSelected
                checkmark.visibility =
                    if (checkmark.visibility == View.VISIBLE) View.INVISIBLE
                    else View.VISIBLE

                if (delete.drawable is StateListDrawable) {
                    val state = delete.drawable.current as? AnimatedVectorDrawable
                    state?.start()
                }

            }

            // checkmark
            checkmark.setOnClickListener { deleteElements() }
        }
    }

    private fun unselectAllElements() {
        val itemCount = ticketsAdapter?.itemCount ?: 0

        for (i in 0 until itemCount) {
            binding.rvContent.post {
                val holder =
                    binding.rvContent.findViewHolderForAdapterPosition(i)
                if (holder != null) {
                    val vh = holder as ChangeTicketAdapter.TicketsViewHolder
                    vh.binding.root.isSelected = false
                }
            }
        }
        ticketsAdapter?.selectedTickets?.clear()
    }

    private fun addNewElement() {
        val tickets = ticketsAdapter?.selectedTickets!!
        val locale = tickets.filter { it.deleteType == DeleteType.LOCAL }
        if (locale.isEmpty()) {
            ticketsAdapter?.addItem(
                ChangeTicketDto(
                    TicketDto(
                        name = "",
                        timer = 0L,
                        tqs = emptyList(),
                        achievement = null
                    ), DeleteType.LOCAL
                )
            )
            return
        }

        Toast.makeText(requireContext(), "Вы уже создали новый билет", Toast.LENGTH_SHORT).show()
    }

    private fun deleteElements() {
        vm.setDeleteState(DeletedUiState.Loading)
        val tickets = ticketsAdapter?.selectedTickets!!

        val locale = tickets.filter { it.deleteType == DeleteType.LOCAL }
        val remote = tickets.filter { it.deleteType == DeleteType.REMOTE }

        when {
            locale.isNotEmpty() && remote.isEmpty() -> {
                ticketsAdapter?.removeItems(locale)
                vm.setDeleteState(DeletedUiState.Deleted)
            }

            remote.isNotEmpty() -> {
                vm.deleteTickets(remote.map { it.item })
                ticketsAdapter?.removeItems(remote)
            }
        }

        ticketsAdapter?.selectedTickets?.clear()
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                tickets.collect {
                    when (it) {
                        is ServerResponse.OK -> {
                            val data = it.value?.toMutableList() ?: mutableListOf()
                            ticketsAdapter?.tickets =
                                data.map { tick -> ChangeTicketDto(tick, DeleteType.REMOTE) }
                                    .toMutableList()
                            ticketsAdapter?.notifyItemRangeChanged(
                                0,
                                ticketsAdapter?.tickets!!.size
                            )
                        }

                        is ServerResponse.Loading -> {
                            Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                        }

                        is ServerResponse.Error -> {
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> {}
                    }
                }
            }
            lifecycleScope.launch {
                isTicketInserted.collectLatest {
                    when (it) {
                        is InsertedUiState.Loading -> {
                            Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                        }

                        is InsertedUiState.Inserted -> {
                            Toast.makeText(
                                requireContext(),
                                "Successfully inserted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is InsertedUiState.Failed -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            lifecycleScope.launch {
                isTicketsDeleted.collectLatest {
                    when (it) {
                        is DeletedUiState.Loading -> {
                            Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                        }

                        is DeletedUiState.Deleted -> {
                            vm.isRemoved = false
                            ticketsAdapter?.isRemoving = false
                            binding.checkmark.visibility = View.INVISIBLE
                            binding.delete.isSelected = false
                        }

                        is DeletedUiState.Failed -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}


private fun createItemDecoration(spacing: Int): RecyclerView.ItemDecoration {
    return object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = spacing
        }
    }
}