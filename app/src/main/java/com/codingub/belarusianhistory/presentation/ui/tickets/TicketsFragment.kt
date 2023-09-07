package com.codingub.belarusianhistory.presentation.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentTicketsBinding
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment


class TicketsFragment : BaseFragment() {

    private lateinit var binding: FragmentTicketsBinding


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentTicketsBinding.inflate(inf, con, false)
        return binding.root
    }
}