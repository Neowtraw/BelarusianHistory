package com.codingub.belarusianhistory.presentation.ui.tickets_practice.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.databinding.FragmentPracticeBinding
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.presentation.ui.tickets_practice.MainItemDecorator
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PracticeFragment : BaseFragment() {

    private val vm : PracticeViewModel by viewModels()
    private lateinit var binding: FragmentPracticeBinding
    private lateinit var adapter: PracticeAdapter

    private val practiceList = mutableListOf<TicketQuestion>()


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentPracticeBinding.inflate(inf, con, false)

        val itemDecoration = MainItemDecorator(6.dp, 3)

        binding.tvHeader.typeface = Font.EXTRABOLD

        adapter = PracticeAdapter(practiceList)
        binding.rvPractice.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPractice.adapter = adapter
        binding.rvPractice.addItemDecoration(itemDecoration)

        observeChanges()
        return binding.root
    }

    override fun observeChanges() {
        with(vm){
            practice.observe(this@PracticeFragment){
                practiceList.clear()
                practiceList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}