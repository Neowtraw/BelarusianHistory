package com.codingub.belarusianhistory.ui.fragments.change

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentChangeItemsBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment

class ChangePqFragment : BaseFragment() {

    private lateinit var binding: FragmentChangeItemsBinding

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentChangeItemsBinding.inflate(inf, con, false)


        return binding.root
    }

    private fun setupListeners() {

    }

    private fun createPqList() {

    }
}