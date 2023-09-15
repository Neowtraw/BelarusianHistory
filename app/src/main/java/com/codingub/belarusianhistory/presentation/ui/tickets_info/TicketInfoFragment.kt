package com.codingub.belarusianhistory.presentation.ui.tickets_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.databinding.FragmentTicketInfoBinding
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.presentation.ui.base.SharedViewModel
import com.codingub.belarusianhistory.presentation.ui.tickets_practice.tickets.TicketAdapter
import com.codingub.belarusianhistory.utils.Font
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketInfoFragment : BaseFragment(){

    private lateinit var binding: FragmentTicketInfoBinding
    private val model: SharedViewModel by activityViewModels()

    private lateinit var adapter: TicketInfoAdapter

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentTicketInfoBinding.inflate(inf, con, false)
        observeChanges()
         return binding.root
    }


    private fun updateTicket(ticket: Ticket){
        binding.tvTicketNumber.apply{
            text = ticket.name
            typeface = Font.EXTRABOLD
        }
        adapter = TicketInfoAdapter()
        adapter.tickets = ticket.questionList
        binding.rvTicketInfo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTicketInfo.adapter = adapter

    }


    override fun observeChanges() {
        with(model){
            ticketInfo.observe(viewLifecycleOwner){
                updateTicket(it)
            }
        }
    }
}