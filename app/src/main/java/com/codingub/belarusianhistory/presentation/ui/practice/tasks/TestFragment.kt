package com.codingub.belarusianhistory.presentation.ui.practice.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentTestBinding
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment

class TestFragment : BaseFragment() {

    private lateinit var binding : FragmentTestBinding

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentTestBinding.inflate(inf, con, false)

        createTestView()

        return binding.root
    }

    private fun createTestView(){
        binding.rvChoose.apply {

        }
    }
}