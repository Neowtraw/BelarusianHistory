package com.codingub.belarusianhistory.ui.fragments.change

import android.graphics.Rect
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.util.Log
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
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.viewmodels.change.ChangeTicketViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangeTicketFragment : BaseFragment() {

    private lateinit var binding: FragmentChangeItemsBinding
    private var ticketsAdapter: ChangeTicketAdapter? = null

    private val vm by viewModels<ChangeTicketViewModel>()

    val deletedTickets: MutableList<TicketDto> = mutableListOf()

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
            ticketsAdapter = ChangeTicketAdapter {
                deletedTickets.add(it)
            }
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ticketsAdapter
            addItemDecoration(createItemDecoration(6.dp))
        }
    }

    private fun selectListeners() {
        with(binding) {

            plus.setOnClickListener {
                addNewElement()
            }
            delete.setOnClickListener {
                vm.isRemoved = !vm.isRemoved.also { bool ->
                    if (bool) {
                        Log.d("", ticketsAdapter!!.tickets.size.toString())
                        ticketsAdapter!!.tickets.forEachIndexed { i, _ ->
                            binding.rvContent.post {
                                val holder =
                                    binding.rvContent.findViewHolderForAdapterPosition(i) as ChangeTicketAdapter.TicketsViewHolder
                                holder.binding.root.isSelected = false
                            }
                        }

                        deletedTickets.clear()
                    }
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
            checkmark.setOnClickListener {
                deleteElements()
            }

        }
    }

    private fun addNewElement() {
        ticketsAdapter?.tickets?.add(
            index = 0,
            element = TicketDto(name = "", timer = 0L, tqs = emptyList(), achievement = null)
        )
        ticketsAdapter?.notifyItemRangeChanged(0, ticketsAdapter?.tickets!!.size)
        vm.setChanged()
    }

    private fun deleteElements() {
        vm.deleteTickets(deletedTickets)
        vm.isRemoved = false
        binding.checkmark.visibility = View.INVISIBLE
        binding.delete.isSelected = false

        deletedTickets.clear()
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                tickets.collect {
                    when (it) {
                        is ServerResponse.OK -> {
                            ticketsAdapter?.tickets = it.value?.toMutableList() ?: mutableListOf()
                            ticketsAdapter?.notifyItemRangeChanged(
                                0,
                                ticketsAdapter?.tickets!!.size
                            )
                        }

                        is ServerResponse.Loading -> {
                            Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()

                        }

                        is ServerResponse.Error -> {
                            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                        }

                        else -> {}
                    }
                }
            }
            lifecycleScope.launch {
                isChanged.collect { if (isChanged.value) binding.fab.visibility = View.VISIBLE }
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
            val position = parent.getChildAdapterPosition(view)

            outRect.bottom = spacing
            if (position % 3 == 0 && position != 0) {
                outRect.top = spacing * 4
            }
        }
    }
}