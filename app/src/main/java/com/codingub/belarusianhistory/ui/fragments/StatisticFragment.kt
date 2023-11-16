package com.codingub.belarusianhistory.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.local.pref.UserConfig
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentStatisticBinding
import com.codingub.belarusianhistory.sdk.AccessLevel
import com.codingub.belarusianhistory.ui.adapters.GroupAdapter
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialog
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialogView
import com.codingub.belarusianhistory.ui.custom.view.statistic.StatisticUserView
import com.codingub.belarusianhistory.ui.viewmodels.StatisticViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ItemDecoration
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatisticFragment : BaseFragment() {

    private val vm: StatisticViewModel by viewModels()
    private lateinit var binding: FragmentStatisticBinding

    private lateinit var groupAdapter: GroupAdapter
    private lateinit var statisticUserView: StatisticUserView

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
        binding.tvGroupName.typeface = Font.EXTRABOLD
        binding.dialogGroupName.typeface = Font.EXTRABOLD
        binding.btnDeleteGroup.typeface = Font.REGULAR
        binding.btnGroupsRepeat.typeface = Font.REGULAR
        binding.groupsEmpty.typeface = Font.REGULAR

        if(UserConfig.getAccessLevel() == AccessLevel.User){
            binding.groupsEmpty.text = Resource.string(R.string.user_groups_empty)
        }
        binding.groupsEmpty.text = Resource.string(R.string.teacher_groups_empty)
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

        val layoutParams = statisticUserView.layoutParams as LinearLayout.LayoutParams
        layoutParams.setMargins(0, 0, 0, 20.dp)
    }

    private fun createGroupView() {
        binding.rvGroups.apply {
            groupAdapter = GroupAdapter()
            adapter = groupAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(ItemDecoration.createLinBottomItemDecoration(9.dp))
        }
    }

    private fun setupListeners() {
        binding.groupsError.setOnClickListener {
            vm.getGroups()
        }
        binding.btnAddGroup.setOnClickListener {
            // открытие Alert Dialog с вводом текста
        }
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    groupState.collectLatest {
                        when(it){
                            is ServerResponse.Loading -> {
                                binding.rvGroups.visibility = View.GONE
                                binding.groupsEmpty.visibility = View.GONE
                                binding.groupsError.visibility = View.GONE

                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is ServerResponse.OK -> {
                                binding.progressBar.visibility = View.GONE

                                groupAdapter.groups = it.value!!
                                groupAdapter.notifyItemRangeChanged(0,groupAdapter.itemCount)

                                if(it.value.isEmpty()){
                                    binding.groupsEmpty.visibility = View.VISIBLE
                                    return@collectLatest
                                }

                                binding.rvGroups.visibility = View.VISIBLE
                            }
                            is ServerResponse.Error -> {
                                binding.progressBar.visibility = View.GONE

                                binding.groupsError.visibility = View.VISIBLE
                            }
                            else->{}
                        }


                    }
                }
            }
        }
    }

    /*
        Additional
     */
//
//    private fun showAddGroupAlertDialog() {
//        if (alertDialog != null) return
//
//        val view = AlertDialogView.Builder(this)
//            .message(R.string.back_task)
//            .positiveButton(R.string.back_task_pos_button) {
//                pushFragment(MenuFragment(), "menu", R.id.fragment_container_view)
//                alertDialog?.dismiss()
//            }
//            .negativeButton(R.string.back_task_neg_button) {
//                alertDialog?.dismiss()
//            }
//            .build()
//
//        alertDialog = AlertDialog(this).apply {
//            setView(view)
//            setOnDismissListener {
//                alertDialog = null
//            }
//        }.also { it.show() }
//    }
}