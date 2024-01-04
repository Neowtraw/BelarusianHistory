package com.codingub.belarusianhistory.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.databinding.FragmentDateOrderBinding
import com.codingub.belarusianhistory.sdk.UserPracticeAnswer
import com.codingub.belarusianhistory.ui.base.TaskFragment
import com.codingub.belarusianhistory.ui.adapters.practice.DateOrderAdapter
import com.codingub.belarusianhistory.ui.custom.item_decoration.DateOrderItemDecoration
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.serializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DateOrderFragment : TaskFragment() {

    private lateinit var binding: FragmentDateOrderBinding

    private lateinit var dateAdapter: DateOrderAdapter
    private lateinit var dateItemDecorator: DateOrderItemDecoration
    private lateinit var question: PracticeQuestion

    private var userAnswer: UserPracticeAnswer? = null

    override fun create() {
        super.create()

        question = (requireArguments().serializable("ticket") as? PracticeQuestion)!!
    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentDateOrderBinding.inflate(inf, con, false)

        createDescription()
        createDateOrderView()

        return binding.root
    }

    private fun createDescription() {
        binding.tvQuestion.apply {
            typeface = Font.EXTRABOLD
            text = question.name
        }
        binding.tvDescription.apply {
            typeface = Font.REGULAR
            text = question.info
        }
    }

    private fun createDateOrderView() {
        binding.rvDate.apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            dateAdapter = DateOrderAdapter(question.answers)
            adapter = dateAdapter
            dateItemDecorator = DateOrderItemDecoration()
            addItemDecoration(dateItemDecorator)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onAnswersChecked(): UserPracticeAnswer? {
        val answers = question.answers.map {
            it.info
        }
        userAnswer = dateAdapter.getUserAnswers()?.let {
            UserPracticeAnswer(
                question.info, it,
                answers,
                dateAdapter.getUserAnswers() == answers
            )
        }
        if(userAnswer == null)
            Toast.makeText(requireContext(), Resource.string(R.string.null_task_request), Toast.LENGTH_SHORT).show()

        return userAnswer
    }

}