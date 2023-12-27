package com.codingub.belarusianhistory.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentEventsBinding
import com.codingub.belarusianhistory.sdk.EventType
import com.codingub.belarusianhistory.ui.adapters.EventTableAdapter
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.viewmodels.EventsViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventsFragment : BaseFragment() {

    private lateinit var binding: FragmentEventsBinding
    private val vm by viewModels<EventsViewModel>()

    private lateinit var dataAdapter: EventTableAdapter
    private lateinit var definitionAdapter: EventTableAdapter

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentEventsBinding.inflate(inf, con, false)

        createHeaders()
        createDataTable()
        createDefinitionTable()

        observeChanges()
        return binding.root
    }

    private fun createHeaders() {
        binding.tvHeader.typeface = Font.EXTRABOLD
        binding.tvDatas.typeface = Font.EXTRABOLD
        binding.tvDefinitions.typeface = Font.EXTRABOLD
    }

    private fun createDataTable() {
        binding.dataHeader.apply {
            tvFirst.typeface = Font.SEMIBOLD
            tvFirst.text = Resource.string(R.string.data)
            tvSecond.typeface = Font.SEMIBOLD
            tvSecond.text = Resource.string(R.string.event)
        }
        binding.rvDatas.apply {
            dataAdapter = EventTableAdapter()
            adapter = dataAdapter
            layoutManager = LinearLayoutManager(requireContext())
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    private fun createDefinitionTable() {
        binding.definitionHeader.apply {
            tvFirst.typeface = Font.SEMIBOLD
            tvSecond.typeface = Font.SEMIBOLD

        }
        binding.rvDefinitions.apply {
            definitionAdapter = EventTableAdapter()
            adapter = definitionAdapter
            layoutManager = LinearLayoutManager(requireContext())
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                events.collectLatest { response ->
                    when(response) {
                        is ServerResponse.Loading -> {

                        }
                        is ServerResponse.OK -> {
                            Log.d("events", response.data.toString())
                            definitionAdapter.events = response.value?.filter { it.type == EventType.DEFINITION } ?: emptyList()
                            dataAdapter.events = response.value?.filter { it.type == EventType.DATA }  ?: emptyList()

                            definitionAdapter.notifyItemRangeChanged(0, dataAdapter.events.size)
                            dataAdapter.notifyItemRangeChanged(0, dataAdapter.events.size)
                        }
                        is ServerResponse.Error -> {
                            Log.d("events", response.errorMessage)

                        }
                        else -> {}
                    }
                }
            }
        }
    }
}