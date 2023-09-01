package com.codingub.belarusianhistory.presentation.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.FragmentMenuBinding
import com.codingub.belarusianhistory.presentation.ui.custom.view.MainHorizontalView
import com.codingub.belarusianhistory.presentation.ui.custom.view.MainSquareView
import com.codingub.belarusianhistory.presentation.ui.custom.view.MainVerticalView


class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    private lateinit var rl : RelativeLayout
    private lateinit var mainEvents: MainHorizontalView
    private lateinit var mainPractice: MainSquareView
    private lateinit var mainTickets: MainSquareView
    private lateinit var mainAchieves: MainVerticalView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createMenuView()
    }

    //Creation
    fun createMenuView(){

    }

}