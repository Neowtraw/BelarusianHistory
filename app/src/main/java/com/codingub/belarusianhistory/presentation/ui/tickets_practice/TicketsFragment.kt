package com.codingub.belarusianhistory.presentation.ui.tickets_practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.databinding.FragmentTicketsBinding
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.Font


class TicketsFragment : BaseFragment() {

    private lateinit var binding: FragmentTicketsBinding
    private lateinit var adapter: MainAdapter


    private val ticketList1= mutableListOf<Ticket>()


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentTicketsBinding.inflate(inf, con, false)

        adapter = MainAdapter(ticketList1)
        binding.rvTicket.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTicket.adapter = adapter

        binding.tvHeader.typeface = Font.EXTRABOLD
        return binding.root
    }
}