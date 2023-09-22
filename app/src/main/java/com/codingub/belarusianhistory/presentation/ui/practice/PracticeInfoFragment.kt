package com.codingub.belarusianhistory.presentation.ui.practice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.FragmentPracticeInfoBinding
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.presentation.ui.base.SharedViewModel
import com.codingub.belarusianhistory.presentation.ui.practice.tasks.TestFragment
import com.codingub.belarusianhistory.utils.Font
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PracticeInfoFragment : BaseFragment() {

    private val model: SharedViewModel by activityViewModels()
    private val vm: PracticeInfoViewModel by viewModels()

    private lateinit var binding: FragmentPracticeInfoBinding

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentPracticeInfoBinding.inflate(inf, con, false)

        observeChanges()
        return binding.root
    }

    private fun createUpBar(){

        binding.tvTheme.apply {
            typeface = Font.EXTRABOLD
            text = model.practiceInfo.value!!.name
        }

        binding.progressBar.apply {
            val size = model.practiceInfo.value!!.practiceList.size
            max = size
            //поменять setOnClickListener на изменение фрагмента
            setOnClickListener {
                if(progress > size) progress = size
                else progress += 1

            }
        }
    }

    private fun createFragmentContainerView(){

        //ТЕСТОВЫЙ ВАРИАНТ ПЕРЕДАЧИ ПАРАМЕТРОВ
        //МОЖЕТ БЫТЬ ПРОБЛЕМА С NULLPOINTEREXCEPTION
        val fragment = TestFragment()
        val args = Bundle().apply {
            putSerializable("ticket", model.practiceInfo.value!!.practiceList.last())
        }
        fragment.arguments = args
        parentFragmentManager.beginTransaction()
            .replace(R.id.fcvTasks, fragment)
            .addToBackStack(null).commit()

    }

    private fun updateUI(){
        createUpBar()
        createFragmentContainerView()
    }

    override fun observeChanges() {

        with(model){
            practiceInfo.observe(viewLifecycleOwner){
               vm.select(it)
                updateUI()
            }
        }


    }


}