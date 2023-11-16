package com.codingub.belarusianhistory.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.remote.network.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.databinding.FragmentInputTextBinding
import com.codingub.belarusianhistory.sdk.UserPracticeAnswer
import com.codingub.belarusianhistory.ui.base.TaskFragment
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialog
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.serializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputTextFragment : TaskFragment() {

    private lateinit var binding: FragmentInputTextBinding

    private lateinit var question: PracticeQuestion
    private var alertDialog: AlertDialog? = null

    private var userAnswer: UserPracticeAnswer? = null
    private var answer: String? = null

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentInputTextBinding.inflate(inf, con, false)

        question = (requireArguments().serializable("ticket") as? PracticeQuestion)!!

        createTextDescription()
        createEditText()

        return binding.root
    }

    private fun createTextDescription() {

        binding.tvQuestion.apply {
            text = question.name
            typeface = Font.EXTRABOLD
        }

        binding.tvDescription.apply {
            text = question.info
            typeface = Font.REGULAR
        }
    }

    private fun createEditText() {
        binding.edInput.apply {
            typeface = Font.EXTRABOLD
            isSingleLine = true
            ellipsize = TextUtils.TruncateAt.END

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun afterTextChanged(s: Editable?) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    answer = text.toString()
                }
            })
        }
    }

    override fun onAnswersChecked(): UserPracticeAnswer? {

        if (answer == "" || answer == null) {
            Toast.makeText(requireContext(),Resource.string(R.string.null_task_request),Toast.LENGTH_SHORT).show()
            return null
        }

        //логика передачи данных в другой фрагмент
        userAnswer = UserPracticeAnswer(
            question.info, listOf(answer!!),
            listOf(question.answers.last().info),
            question.answers.last().info == answer
        )

        return userAnswer
    }
}