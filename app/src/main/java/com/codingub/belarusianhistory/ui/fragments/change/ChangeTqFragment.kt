package com.codingub.belarusianhistory.ui.fragments.change

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentChangeItemsBinding
import com.codingub.belarusianhistory.ui.adapters.change.ChangePracticeAdapter
import com.codingub.belarusianhistory.ui.adapters.change.ChangeTicketAdapter
import com.codingub.belarusianhistory.ui.adapters.change.ChangeTicketQuestionAdapter
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.Font
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeTqFragment : BaseFragment() {

    private lateinit var binding: FragmentChangeItemsBinding
    private var tqAdapter: ChangeTicketQuestionAdapter? = null

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentChangeItemsBinding.inflate(inf, con, false)

        createTexts()
        selectListeners()
        return binding.root
    }

    private fun createTexts() {
        with(binding){
            tvHeader.typeface = Font.EXTRABOLD
        }
    }

    private fun selectListeners() {
        with(binding){

            plus.setOnClickListener {

            }

        }
    }



}