package com.codingub.belarusianhistory.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.codingub.belarusianhistory.databinding.FragmentMenuBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.fragments.practice.PracticeFragment
import com.codingub.belarusianhistory.ui.fragments.ticket.EventsFragment
import com.codingub.belarusianhistory.ui.fragments.ticket.TicketsFragment
import com.codingub.belarusianhistory.ui.viewmodels.MenuViewModel
import com.codingub.belarusianhistory.utils.Font
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MenuFragment : BaseFragment() {

    private val vm: MenuViewModel by viewModels()
    private lateinit var binding: FragmentMenuBinding


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inf, con, false)

        createMenuEvents()
        createMenuPractice()
        createMenuTickets()
        createMenuAchieves()
        setupListeners()

        observeChanges()

        binding.tvHeader.typeface = Font.EXTRABOLD

        return binding.root
    }

    //Creation
    private fun createMenuEvents() {
        binding.tvEvents.typeface = Font.EXTRABOLD
        binding.tvEventsInfo.typeface = Font.REGULAR
    }

    private fun createMenuPractice() {
        binding.tvPractice.typeface = Font.EXTRABOLD
        binding.tvPracticeScore.typeface = Font.REGULAR
    }

    private fun createMenuTickets() {
        binding.tvTickets.typeface = Font.EXTRABOLD
        binding.tvTicketsScore.typeface = Font.REGULAR
    }

    private fun createMenuAchieves() {
        binding.tvAchieves.typeface = Font.EXTRABOLD
        binding.tvAchievesInfo.typeface = Font.REGULAR
        binding.tvAchievesScore.typeface = Font.REGULAR
    }

    private fun setupListeners() {
        binding.cvTickets.setOnClickListener {
            pushFragment(TicketsFragment(), "tickets")
        }
        binding.cvAchieves.setOnClickListener {
            pushFragment(AchieveFragment(), "achieves")
        }
        binding.cvPractice.setOnClickListener {
            pushFragment(PracticeFragment(), "practice")
        }
        binding.cvEvents.setOnClickListener {
            pushFragment(EventsFragment(), "events")
        }

    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                tickets.collect {
                    withContext(Dispatchers.Main) {
                        binding.tvTicketsScore.text = it
                    }
                }
            }
            lifecycleScope.launch {

                achieves.collect {
                    withContext(Dispatchers.Main) {
                        binding.tvAchievesScore.text = it
                    }
                }
            }
            lifecycleScope.launch {

                practice.collect {
                    withContext(Dispatchers.Main) {
                        binding.tvPracticeScore.text = it
                    }
                }
            }
        }
    }
}