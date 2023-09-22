package com.codingub.belarusianhistory.presentation.ui.practice.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentConnectionBinding
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment

class ConnectionFragment(val question: TicketQuestion) : BaseFragment() {

    private lateinit var binding: FragmentConnectionBinding

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentConnectionBinding.inflate(inf, con, false)

        return binding.root
    }
}