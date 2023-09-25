package com.codingub.belarusianhistory.ui.practice.tasks.input

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentInputTextBinding
import com.codingub.belarusianhistory.domain.model.PracticeQuestion
import com.codingub.belarusianhistory.ui.base.TaskFragment
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.serializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputTextFragment : TaskFragment() {

    private lateinit var binding: FragmentInputTextBinding

    private lateinit var question : PracticeQuestion
    private var answer = ""

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentInputTextBinding.inflate(inf, con, false)

        question = (requireArguments().serializable("ticket") as? PracticeQuestion)!!

        createTextDescription()
        createEditText()

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

    private fun createEditText(){
        binding.edInput.apply {
            typeface = Font.EXTRABOLD
            isSingleLine = true
            ellipsize = TextUtils.TruncateAt.END
        }
    }

    override fun onAnswersChecked() : Boolean{

        if(answer == "") //AlertDialog о неизмененных данных
        else { //логика передачи данных в другой фрагмент
        }

        return true
    }

}