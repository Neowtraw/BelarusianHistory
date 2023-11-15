package com.codingub.belarusianhistory.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.codingub.belarusianhistory.data.local.pref.UserConfig
import com.codingub.belarusianhistory.databinding.FragmentStatisticBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.custom.view.statistic.StatisticGroupView
import com.codingub.belarusianhistory.ui.custom.view.statistic.StatisticUserView
import com.codingub.belarusianhistory.ui.viewmodels.StatisticViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticFragment : BaseFragment() {

    private val vm: StatisticViewModel by viewModels()
    private lateinit var binding: FragmentStatisticBinding

    private lateinit var statisticUserView: StatisticUserView
    private lateinit var statisticGroupView: StatisticGroupView

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentStatisticBinding.inflate(inf, con, false)

        createUI()
        createUserView()
        createGroupView()

        setupListeners()
        observeChanges()
        return binding.root
    }

    private fun createUI() {
        binding.tvStatistic.typeface = Font.EXTRABOLD
        binding.GroupName.typeface = Font.EXTRABOLD
        binding.btnDeleteGroup.typeface = Font.REGULAR
    }

    private fun createUserView() {

        //TEST!!!
        statisticUserView = StatisticUserView(
            context = context,
            username = UserConfig.getUsername(),
            passedTickets = 1,
            allTickets = 2,
            passedPractice = 1,
            allPractice = 3,
            passedAchieves = 2,
            allAchieves = 5
        )
        binding.llStatistic.addView(
            statisticUserView, ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
        )
    }

    private fun createGroupView() {
        statisticGroupView = StatisticGroupView(

            context = context,
            groups = listOf(
                //TEST!!!
            )
        )
        binding.llStatistic.addView(
            statisticGroupView, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
        )

        val layoutParams = statisticGroupView.layoutParams as LinearLayout.LayoutParams
        layoutParams.setMargins(0, 20.dp, 0, 0)
    }

    private fun setupListeners() {

    }

    override fun observeChanges() {
        with(vm) {

        }
    }

}