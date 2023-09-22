package com.codingub.belarusianhistory.presentation.ui.practice.tasks

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.codingub.belarusianhistory.databinding.FragmentTestBinding
import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.domain.model.Answer
import com.codingub.belarusianhistory.domain.model.PracticeQuestion
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.sdk.TaskType
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.serializable
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class TestFragment : BaseFragment() {


    private lateinit var binding : FragmentTestBinding
    private lateinit var testAdapter: TestAdapter

    private lateinit var question : PracticeQuestion

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
            //  !!! разкоментить после добавления логики передачи самого вопроса
            testAdapter = TestAdapter(listOf(Answer(0,"хуцй",1,0),
                Answer(1,"ух",0,0),
                Answer(2,"помогите",0,0),
                Answer(3,"чух чух чух",0,0),))
                ///////
            adapter = testAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }
    }
}
