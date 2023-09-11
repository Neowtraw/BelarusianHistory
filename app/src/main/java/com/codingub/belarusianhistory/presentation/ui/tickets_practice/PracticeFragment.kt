package com.codingub.belarusianhistory.presentation.ui.tickets_practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.databinding.FragmentPracticeBinding
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.Font

class PracticeFragment : BaseFragment() {
    private lateinit var binding: FragmentPracticeBinding
    private lateinit var adapter: MainAdapter

    private val ticketList1= mutableListOf<Ticket>()


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentPracticeBinding.inflate(inf, con, false)

        adapter = MainAdapter(ticketList1)
        binding.rvPractice.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPractice.adapter = adapter

        binding.tvHeader.typeface = Font.EXTRABOLD
        return binding.root
    }
}