package com.codingub.belarusianhistory.ui.practice.tasks.date_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.databinding.FragmentDateOrderBinding
import com.codingub.belarusianhistory.domain.model.PracticeQuestion
import com.codingub.belarusianhistory.ui.base.TaskFragment
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.serializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DateOrderFragment : TaskFragment() {

    private lateinit var binding: FragmentDateOrderBinding

    private lateinit var dateAdapter: DateOrderAdapter
    private lateinit var dateItemDecorator: DateOrderItemDecorator
    private lateinit var question: PracticeQuestion

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

    private fun createDescription(){
        binding.tvQuestion.apply {
            typeface = Font.EXTRABOLD
            text = question.name
        }
        binding.tvDescription.apply {
            typeface = Font.REGULAR
            text = question.info
        }
    }

    private fun createDateOrderView(){
        binding.rvDate.apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            dateAdapter = DateOrderAdapter(question.answers)
            adapter = dateAdapter
            dateItemDecorator = DateOrderItemDecorator()
            addItemDecoration(dateItemDecorator)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onAnswersChecked(): Boolean {



        return true
    }

}