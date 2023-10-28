package com.codingub.belarusianhistory.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.FragmentMenuBinding
import com.codingub.belarusianhistory.ui.achieves.AchieveFragment
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.custom.view.MainHorizontalView
import com.codingub.belarusianhistory.ui.custom.view.MainSquareView
import com.codingub.belarusianhistory.ui.custom.view.MainVerticalView
import com.codingub.belarusianhistory.ui.tickets_practice.practice.PracticeFragment
import com.codingub.belarusianhistory.ui.tickets_practice.tickets.TicketsFragment
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment() {

    private val vm: MenuViewModel by viewModels()

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

        observeChanges()

        binding.tvHeader.typeface = Font.EXTRABOLD

        return binding.root
    }

    //Creation
    private fun createMenuEvents(){
        menuEvents = MainHorizontalView(
            context = requireContext(), src = "events",
            textName = Resource.string(R.string.events),
            textInfo = Resource.string(R.string.events_info),
            textAchieves = "0/1").apply {
            id =View.generateViewId()
            setOnClickListener{
                pushFragment(MenuFragment(), "events")
            }
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
            textName = Resource.string(R.string.practice),
            textAchieves = "",
            R.color.top_color_practice,
            R.color.bottom_color_practice).apply {
            id =View.generateViewId()
            setOnClickListener {
                pushFragment(PracticeFragment(), "practice")
            }
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
            textName = Resource.string(R.string.tickets),
            textAchieves = "",
            R.color.top_color_tickets,
            R.color.bottom_color_tickets).apply {
            id =View.generateViewId()
            setOnClickListener{
                pushFragment(TicketsFragment(), "tickets")
            }
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
            textName = Resource.string(R.string.achieves),
            textAchieves = "",
            textInfo = Resource.string(R.string.achieves_info)
        ).apply {
            id =View.generateViewId()
            setOnClickListener{
                pushFragment(AchieveFragment(), "achieves")
            }
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

    override fun observeChanges() {
        with(vm){
            ticketAchieves.observe(this@MenuFragment){
                menuTickets.infoText = it.toString()
            }
            practiceAchieves.observe(this@MenuFragment){
                menuPractice.infoText = it.toString()
            }
            ticketAchievesPassed.observe(this@MenuFragment){
                menuTickets.infoTextPassed = it.toString()
            }
            practiceAchievesPassed.observe(this@MenuFragment){
                menuPractice.infoTextPassed = it.toString()
            }
            allAchieves.observe(this@MenuFragment){
                menuAchieves.infoText = it.toString()
            }
            allAchievesPassed.observe(this@MenuFragment){
                menuAchieves.infoTextPassed = it.toString()
            }
        }

    }

}