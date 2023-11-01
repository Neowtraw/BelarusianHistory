package com.codingub.belarusianhistory.ui.tickets_practice.tickets

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
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.tickets.Ticket
import com.codingub.belarusianhistory.databinding.FragmentTicketsBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.menu.MenuFragment
import com.codingub.belarusianhistory.ui.tickets_info.TicketInfoFragment
import com.codingub.belarusianhistory.ui.tickets_practice.MainItemDecorator
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
    private lateinit var adapter: TicketAdapter


    private val ticketsList = mutableListOf<Ticket>()


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentTicketsBinding.inflate(inf, con, false)
        val itemDecoration = MainItemDecorator(6.dp, 3)

        binding.tvHeader.typeface = Font.EXTRABOLD


        adapter = TicketAdapter(ticketsList, onTicketSelected = { ticket ->
            model.select(ticket)
            pushFragment(TicketInfoFragment(), "ticketInfo")
        })
        binding.rvTicket.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTicket.adapter = adapter
        binding.rvTicket.addItemDecoration(itemDecoration)

        observeChanges()
        return binding.root
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    ticketsState.collectLatest {
                        when (it) {
                            is ServerResponse.Loading -> { // загрузка
                                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG)
                                    .show()
                                /**
                                 * ЗДЕСЬ ТВОЙ КОД
                                 */
                            }

                            is ServerResponse.OK -> {
                                ticketsList.clear()
                                ticketsList.addAll(it.value!!)
                                adapter.notifyDataSetChanged()

                                Toast.makeText(
                                    requireContext(),
                                    "Data was loaded",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is ServerResponse.Conflict -> {
                                Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
                            }
                            is ServerResponse.BadRequest -> {

                            }
                            is ServerResponse.UnknownError -> {

                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}