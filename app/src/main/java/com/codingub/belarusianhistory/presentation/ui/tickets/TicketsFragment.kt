package com.codingub.belarusianhistory.presentation.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.codingub.belarusianhistory.databinding.FragmentTicketsBinding
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.Font


class TicketsFragment : BaseFragment() {

    private lateinit var binding: FragmentTicketsBinding
    private lateinit var adapter: TicketsAdapter

    private val questList1 = mutableListOf<TicketQuestion>()
    private val questList2 = mutableListOf<TicketQuestion>()

    private val ticketList1= mutableListOf<Ticket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        questList2.add(TicketQuestion(1, "А может и не передумала", "", 1, emptyList()))
        questList2.add(TicketQuestion(2, "А может и не передумала", "", 1, emptyList()))

        questList2.add(TicketQuestion(3, "А может и не передумала", "", 1, emptyList()))
        questList2.add(TicketQuestion(4, "А может и не передумала", "", 1, emptyList()))
        questList2.add(TicketQuestion(5, "А может и не передумала", "", 1, emptyList()))

        ticketList1.add(Ticket(1,"хуйня первая", 1,questList1, TicketAchieves(1,"","",1)))
        ticketList1.add(Ticket(2,"хуйня вторая", 0,questList2, TicketAchieves(2,"","",1)))
    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentTicketsBinding.inflate(inf, con, false)

        adapter = TicketsAdapter(ticketList1)
        binding.rvTicket.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTicket.adapter = adapter

        binding.tvHeader.typeface = Font.EXTRABOLD
        return binding.root
    }
}