package com.codingub.belarusianhistory.ui.fragments.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.data.models.map.MapTypeDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentMapTypeBinding
import com.codingub.belarusianhistory.ui.adapters.TypeAdapter
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.fragments.change.createItemDecoration
import com.codingub.belarusianhistory.ui.viewmodels.map.PeriodViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapTypeFragment : BaseFragment() {

    private lateinit var binding: FragmentMapTypeBinding
    private val vm by viewModels<PeriodViewModel>()

    private lateinit var typeAdapter: TypeAdapter

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentMapTypeBinding.inflate(inf, con, false)

        observeChanges()
        return binding.root
    }

    private fun setMainText() {
        binding.tvHeader.typeface = Font.SEMIBOLD
    }

    private fun setTypes(types: List<MapTypeDto>) {
        binding.rvTypes.apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(createItemDecoration(10.dp))
            typeAdapter = TypeAdapter(types) {

            }
            adapter = typeAdapter
        }
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch(Dispatchers.IO) {
                types.collectLatest { result ->
                    when (result) {
                        is ServerResponse.OK -> {
                            setTypes(result.value!!)
                        }

                        is ServerResponse.Loading -> {
                            /*
                                Loading
                             */
                        }

                        is ServerResponse.Error -> {
                            Log.d("exception", result.errorMessage)
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}