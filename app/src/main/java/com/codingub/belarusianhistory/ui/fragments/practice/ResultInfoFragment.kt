package com.codingub.belarusianhistory.ui.fragments.practice

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.databinding.FragmentResultInfoBinding
import com.codingub.belarusianhistory.sdk.UserPracticeAnswer
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.adapters.practice.ResultInfoAdapter
import com.codingub.belarusianhistory.ui.fragments.MenuFragment
import com.codingub.belarusianhistory.ui.viewmodels.practice.ResultInfoViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ItemDecoration
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.serializable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResultInfoFragment : BaseFragment(){

    private val vm: ResultInfoViewModel by viewModels()
    private val model: SharedViewModel by activityViewModels()

    private lateinit var resultAdapter: ResultInfoAdapter

    private lateinit var binding: FragmentResultInfoBinding

    override fun create() {
        super.create()

            val list = (requireArguments().serializable("resultList") as? List<UserPracticeAnswer>)!!
            vm.select(list)
        }


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentResultInfoBinding.inflate(inf, con, false)

        createTexts()
        createResultView()

        observeChanges()

        return binding.root
    }

    private fun createTexts(){
        binding.tvHeader.typeface = Font.EXTRABOLD
        binding.tvResult.typeface = Font.EXTRABOLD
        binding.btnGoTo.apply {
            typeface = Font.EXTRABOLD
            setOnClickListener {
                pushFragment(MenuFragment(), "menu")
            }
        }
    }

    private fun createResultView(){
        binding.rvResult.apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            resultAdapter = ResultInfoAdapter()
            adapter = resultAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ItemDecoration.createLinBottomItemDecoration(9.dp))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(){
        binding.tvResult.text = vm.userAnswerCount.toString() +
                "/" + vm.allAnswerCount.toString()
    }

    override fun observeChanges() {
        with(model){
            practiceInfo.observe(viewLifecycleOwner){
                vm.allAnswerCount = it.practices!!.count { question ->
                    question.answers.any { answer -> answer.isTrue }
                }
                updateUI()

            }
        }

        with(vm){
            resultList.observe(this@ResultInfoFragment){
                resultAdapter.results = it
                updateUI()
            }
        }
    }
}