package com.codingub.belarusianhistory.presentation.ui.practice.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentDateOrderBinding
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment

class DateOrderFragment : BaseFragment() {

    private lateinit var binding: FragmentDateOrderBinding

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentDateOrderBinding.inflate(inf, con, false)

        return binding.root
    }
}