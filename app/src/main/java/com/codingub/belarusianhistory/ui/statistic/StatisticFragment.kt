package com.codingub.belarusianhistory.ui.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import androidx.fragment.app.viewModels
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.remote.network.models.userdata.Group
import com.codingub.belarusianhistory.data.remote.network.models.userdata.User
import com.codingub.belarusianhistory.databinding.FragmentStatisticBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.custom.view.statistic.StatisticGroupView
import com.codingub.belarusianhistory.ui.custom.view.statistic.StatisticUserView
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp


class StatisticFragment : BaseFragment() {

    private lateinit var binding: FragmentStatisticBinding
    private val vm: StatisticViewModel by viewModels()

    private lateinit var statisticUserView: StatisticUserView
    private lateinit var statisticGroupView: StatisticGroupView

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentStatisticBinding.inflate(inf, con, false)
        createUI()
        return binding.root
    }

    private fun createUI() {

        binding.tvStatistic.typeface = Font.EXTRABOLD

        //UserView
        statisticUserView = StatisticUserView(
            context = context,
            username = "user",
            passedTickets = 1,
            allTickets = 2,
            passedPractice = 1,
            allPractice = 3,
            passedAchieves = 2,
            allAchieves = 5
        )
        binding.root.addView(
            statisticUserView, ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ))

        //GroupView
        statisticGroupView = StatisticGroupView(
            context = context,
            groups = listOf(
                Group(
                    id = "1", name = "1k9393",
                    teacherId = ""
                ),
                Group(
                    id = "2", name = "1k9394",
                    teacherId = ""
                )
            )
        )

        binding.root.addView(
            statisticGroupView, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ).apply {
                setMargins(0,16.dp,0,0)
            }
        )

    }

    override fun observeChanges() {
        with(vm) {

        }
    }

}