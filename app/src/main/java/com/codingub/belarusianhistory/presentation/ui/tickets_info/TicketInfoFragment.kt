package com.codingub.belarusianhistory.presentation.ui.tickets_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentPracticeBinding
import com.codingub.belarusianhistory.databinding.FragmentTicketInfoBinding
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketInfoFragment : BaseFragment(){

    private lateinit var binding: FragmentTicketInfoBinding

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentTicketInfoBinding.inflate(inf, con, false)


         return binding.root
    }
}