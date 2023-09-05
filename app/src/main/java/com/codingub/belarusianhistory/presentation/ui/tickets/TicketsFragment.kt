package com.codingub.belarusianhistory.presentation.ui.tickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentTicketsBinding


class TicketsFragment : Fragment() {

    private lateinit var binding: FragmentTicketsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
}