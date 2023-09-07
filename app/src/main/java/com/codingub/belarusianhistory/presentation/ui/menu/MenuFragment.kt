package com.codingub.belarusianhistory.presentation.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.FragmentMenuBinding
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.presentation.ui.custom.view.MainHorizontalView
import com.codingub.belarusianhistory.presentation.ui.custom.view.MainSquareView
import com.codingub.belarusianhistory.presentation.ui.custom.view.MainVerticalView
import com.codingub.belarusianhistory.sdk.FragmentType
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp


class MenuFragment : BaseFragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var menuEvents: MainHorizontalView
    private lateinit var menuPractice: MainSquareView
    private lateinit var menuTickets: MainSquareView
    private lateinit var menuAchieves: MainVerticalView

    private val menuMargin = 10.dp

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inf, con, false)

        createMenuEvents()
        createMenuPractice()
        createMenuTickets()
        createMenuAchieves()
        binding.tvHeader.typeface = Font.EXTRABOLD



        return binding.root
    }

    override fun viewCreated() {
        super.viewCreated()

        //необходима логика для передачи количества
        // полученных и всех достижений в определенном меню


        replaceFragment()
    }

    //Creation
    private fun createMenuEvents(){
        menuEvents = MainHorizontalView(
            context = requireContext(), src = "events",
            textName = resources.getString(R.string.events),
            textInfo = resources.getString(R.string.events_info),
            textAchieves = "0/1").apply {
            id =View.generateViewId()
        }

        binding.rlMenu.addView(menuEvents, RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            160.dp
        ).apply {
            setMargins(0,0,0, menuMargin)
        })

    }

    private fun createMenuPractice(){
        menuPractice = MainSquareView(
            context = requireContext(), src = "practice",
            textName = resources.getString(R.string.practice),
            textAchieves = "0/1", R.color.top_color_practice,
            R.color.bottom_color_practice).apply {
            id =View.generateViewId()
        }

        binding.rlMenu.addView(menuPractice,RelativeLayout.LayoutParams(
            160.dp,
            160.dp
        ).apply {
            addRule(RelativeLayout.BELOW, menuEvents.id)
            setMargins(0,0,menuMargin,menuMargin)
        })
    }

    private fun createMenuTickets(){
        menuTickets = MainSquareView(
            context = requireContext(), src = "tickets",
            textName = resources.getString(R.string.tickets),
            textAchieves = "0/1", R.color.top_color_tickets,
            R.color.bottom_color_tickets).apply {
            id =View.generateViewId()
        }

        binding.rlMenu.addView(menuTickets, RelativeLayout.LayoutParams(
            160.dp,
            160.dp
        ).apply {
            addRule(RelativeLayout.BELOW, menuPractice.id)
            setMargins(0,0,menuMargin,0)
        })

    }

    private fun createMenuAchieves(){
        menuAchieves = MainVerticalView(
            context = requireContext(), src = "achieves",
            textName = resources.getString(R.string.achieves),
            textAchieves = "0/1",
            textInfo = resources.getString(R.string.achieves_info)
        ).apply {
            id =View.generateViewId()
        }
        binding.rlMenu.addView(menuAchieves,  RelativeLayout.LayoutParams(
            160.dp,
            330.dp
        ).apply {
            addRule(RelativeLayout.BELOW, menuEvents.id)
            addRule(RelativeLayout.RIGHT_OF, menuPractice.id)
        })
    }

    /*
            Action
         */


    fun replaceFragment(){
        menuEvents.setOnClickListener{
            pushFragment(FragmentType.EVENTS.getFragment(), "events")
        }
        menuAchieves.setOnClickListener{
            pushFragment(FragmentType.ACHIEVES.getFragment(), "achieves")
        }
        menuPractice.setOnClickListener{
            pushFragment(FragmentType.PRACTICE.getFragment(), "practice")
        }
        menuTickets.setOnClickListener{
            pushFragment(FragmentType.TICKETS.getFragment(), "tickets")
        }
    }

}