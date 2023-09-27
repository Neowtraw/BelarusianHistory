package com.codingub.belarusianhistory.ui.practice

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.FragmentPracticeInfoBinding
import com.codingub.belarusianhistory.domain.model.PracticeQuestion
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.practice.tasks.date_order.DateOrderFragment
import com.codingub.belarusianhistory.ui.practice.tasks.input.InputTextFragment
import com.codingub.belarusianhistory.ui.practice.tasks.test.TestFragment
import com.codingub.belarusianhistory.sdk.TaskType
import com.codingub.belarusianhistory.sdk.UserPracticeAnswer
import com.codingub.belarusianhistory.ui.base.TaskFragment
import com.codingub.belarusianhistory.ui.practice.result.ResultInfoFragment
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class PracticeInfoFragment : BaseFragment() {

    private val model: SharedViewModel by activityViewModels()
    private val vm: PracticeInfoViewModel by viewModels()

    private lateinit var binding: FragmentPracticeInfoBinding

    private var progressSize: Int = 0

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentPracticeInfoBinding.inflate(inf, con, false)

        createUpBar()

        observeChanges()
        return binding.root
    }

    private fun createUpBar() {
        binding.tvTheme.apply {
            typeface = Font.EXTRABOLD
            text = ""
        }
        binding.progressBar.apply {
            max = progressSize
            progressBackgroundTintList = ColorStateList.valueOf(Resource.color(R.color.bg_btn))
            progressTintList = ColorStateList.valueOf(Resource.color(R.color.contrast))
            //поменять setOnClickListener на изменение фрагмента
            binding.btnCheck.apply {
                typeface = Font.EXTRABOLD
                setOnClickListener {
                    if (progress == progressSize-1){

                        val fragment1 = childFragmentManager.fragments.last() as TaskFragment
                        fragment1.onAnswersChecked()?.let {
                            vm.addUserResult(it)
                            progress += 1
                        }

                        val resultList : List<UserPracticeAnswer> = vm.resultList.value?.toList() ?: emptyList()
                        val args = Bundle().apply {
                            putSerializable("resultList",ArrayList(resultList) as Serializable)
                        }

                        val fragment = ResultInfoFragment()
                        fragment.arguments = args

                        pushFragment(fragment, "resultInfo")
                        return@setOnClickListener
                    }

                    val fragment = childFragmentManager.fragments.last() as TaskFragment

                    fragment.onAnswersChecked()?.let {
                        vm.addUserResult(it)
                        progress += 1
                        vm.getPracticeQuestionByPosition()?.let { it1 -> pushChildFragment(it1) }
                    }

                }
            }
        }
    }


    override fun observeChanges() {

        with(model) {
            practiceInfo.observe(viewLifecycleOwner) {
                vm.selectTicketQuestion(it)
            }
        }

        with(vm) {
            ticketQuestion.observe(this@PracticeInfoFragment) {
                binding.tvTheme.text = it.name

                progressSize = it.practiceList.size
                binding.progressBar.max = progressSize

                //once
                vm.getPracticeQuestionByPosition()?.let { it1 -> pushChildFragment(it1) }

            }
        }


    }

    private fun pushChildFragment(queston: PracticeQuestion) {

        val args = Bundle().apply {
            putSerializable("ticket", queston)
        }

        val fragment = when (queston.taskType) {
            TaskType.Test -> TestFragment()
            TaskType.InputText -> InputTextFragment()
            TaskType.DateOrder -> DateOrderFragment()
        }
        fragment.arguments = args

        pushTaskFragment(this@PracticeInfoFragment, fragment, "")
    }
}
