package com.codingub.belarusianhistory.ui.practice.tasks.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.FragmentTestBinding
import com.codingub.belarusianhistory.domain.model.PracticeQuestion
import com.codingub.belarusianhistory.sdk.UserPracticeAnswer
import com.codingub.belarusianhistory.ui.base.TaskFragment
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.serializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment : TaskFragment() {


    private lateinit var binding : FragmentTestBinding
    private lateinit var testAdapter: TestAdapter

    private lateinit var question : PracticeQuestion
    private var userAnswer: UserPracticeAnswer? = null

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentTestBinding.inflate(inf, con, false)

        //получаем значения через serializable
        question = (requireArguments().serializable("ticket") as? PracticeQuestion)!!

        createTextDescription()
        createTestView()

        return binding.root
    }

    private fun createTextDescription(){
        binding.tvQuestion.apply{

            text = question.name
            typeface = Font.EXTRABOLD
        }
        binding.tvDescription.apply {
            text = question.info
            typeface = Font.REGULAR
        }
    }

    private fun createTestView(){
        binding.rvChoose.apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            testAdapter = TestAdapter(question.answers){userAnswer, answer, isTrue ->
                this@TestFragment.userAnswer =
                    UserPracticeAnswer(question.info, listOf(userAnswer), listOf(answer), isTrue)

            }
            adapter = testAdapter
            layoutManager = GridLayoutManager(requireContext(),2)

        }
    }

    override fun onAnswersChecked(): UserPracticeAnswer? {
        if (userAnswer == null)
            Toast.makeText(requireContext(), Resource.string(R.string.null_task_request), Toast.LENGTH_SHORT).show()

        return userAnswer
    }
}
