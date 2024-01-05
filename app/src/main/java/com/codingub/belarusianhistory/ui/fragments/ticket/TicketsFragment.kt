package com.codingub.belarusianhistory.ui.fragments.ticket

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentTicketsBinding
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.ui.adapters.ShimmerContentListAdapter
import com.codingub.belarusianhistory.ui.adapters.ticket.TicketAdapter
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.viewmodels.TicketsViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TicketsFragment : BaseFragment() {

    private val vm: TicketsViewModel by viewModels()
    private val model: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentTicketsBinding
    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var shimmerAdapter: ShimmerContentListAdapter

    private val ticketsList = mutableListOf<TicketDto>()


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentTicketsBinding.inflate(inf, con, false)

        createUI()
        observeChanges()
        return binding.root
    }

    private fun createUI() {
        binding.tvHeader.typeface = Font.EXTRABOLD
        binding.tvEmptyTicket.typeface = Font.EXTRABOLD

        binding.rvTicket.apply {
            layoutManager = LinearLayoutManager(requireContext())
            ticketAdapter = TicketAdapter(ticketsList, onTicketSelected = { ticket ->
                model.select(ticket)
                pushFragment(TicketInfoFragment(), "ticketInfo")
            })
            adapter = ticketAdapter
            addItemDecoration(createItemDecoration(6.dp))
        }

        binding.rvShimmer.apply {
            layoutManager = LinearLayoutManager(requireContext())
            shimmerAdapter = ShimmerContentListAdapter()
            overScrollMode = View.OVER_SCROLL_NEVER
            adapter = shimmerAdapter
            addItemDecoration(createItemDecoration(6.dp))
        }
    }


    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    ticketsState.collectLatest {
                        when (it) {
                            is ServerResponse.Loading -> {
                                binding.rvShimmer.visibility = View.VISIBLE
                                binding.shimmer.startShimmer()
                                binding.rvTicket.visibility = View.GONE
                                binding.tvEmptyTicket.visibility = View.GONE
                            }

                            is ServerResponse.OK -> {
                                binding.rvShimmer.visibility = View.GONE
                                binding.shimmer.stopShimmer()

                                if (it.value.isNullOrEmpty()) {
                                    binding.tvEmptyTicket.visibility = View.VISIBLE
                                } else {
                                    binding.rvTicket.visibility = View.VISIBLE
                                    ticketsList.clear()
                                    ticketsList.addAll(it.value)
                                    ticketAdapter.notifyDataSetChanged()
                                }
                            }

                            is ServerResponse.Error -> {
                                Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG)
                                    .show()
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    /*
        Additional
     */

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
}