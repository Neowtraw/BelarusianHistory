package com.codingub.belarusianhistory.ui.tickets_practice.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.databinding.FragmentPracticeBinding
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.practice.PracticeInfoFragment
import com.codingub.belarusianhistory.ui.tickets_practice.MainItemDecorator
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PracticeFragment : BaseFragment() {

    private val model: SharedViewModel  by activityViewModels()
    private val vm : PracticeViewModel by viewModels()

    private lateinit var binding: FragmentPracticeBinding
    private lateinit var adapter: PracticeAdapter

    private val practiceList = mutableListOf<TicketQuestion>()


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentPracticeBinding.inflate(inf, con, false)

        val itemDecoration = MainItemDecorator(6.dp, 3)

        binding.tvHeader.typeface = Font.EXTRABOLD
        binding.tvEmptyPractice.typeface = Font.REGULAR

        adapter = PracticeAdapter(practiceList){ practice ->

            model.select(practice)
            pushFragment(PracticeInfoFragment(), "practiceInfoFragment")
        }

        binding.rvPractice.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPractice.adapter = adapter
        binding.rvPractice.addItemDecoration(itemDecoration)

        observeChanges()
        return binding.root
    }

    override fun observeChanges() {
        with(vm){
            practice.observe(this@PracticeFragment){
                if(it.isEmpty()){
                    binding.rvPractice.visibility = View.INVISIBLE
                    binding.tvEmptyPractice.visibility = View.VISIBLE
                } else{
                    binding.rvPractice.visibility = View.VISIBLE
                    binding.tvEmptyPractice.visibility = View.INVISIBLE
                    practiceList.clear()
                    practiceList.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}