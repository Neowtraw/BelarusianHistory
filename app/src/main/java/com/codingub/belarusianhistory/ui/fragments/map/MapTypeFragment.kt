package com.codingub.belarusianhistory.ui.fragments.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.data.models.map.MapTypeDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentMapTypeBinding
import com.codingub.belarusianhistory.databinding.ItemPeriodBinding
import com.codingub.belarusianhistory.ui.adapters.ShimmerContentListAdapter
import com.codingub.belarusianhistory.ui.adapters.ShimmerMapTypeListAdapter
import com.codingub.belarusianhistory.ui.adapters.TypeAdapter
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.fragments.change.createItemDecoration
import com.codingub.belarusianhistory.ui.viewmodels.map.PeriodViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MapTypeFragment : BaseFragment() {

    private var binding: FragmentMapTypeBinding? = null
    private val vm by viewModels<PeriodViewModel>()
    private val sharedVm by activityViewModels<SharedViewModel>()
    private val TAG = MapTypeFragment::class.java.name

    private var typeAdapter: TypeAdapter? = null
    private var shimmerAdapter: ShimmerMapTypeListAdapter? = null

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentMapTypeBinding.inflate(inf, con, false)


        setMainText()
        setShimmer()
        observeChanges()
        return binding!!.root
    }

    override fun destroyView() {
        super.destroyView()
        binding = null
        shimmerAdapter = null
        typeAdapter = null
    }

    private fun setMainText() {
        binding!!.tvHeader.typeface = Font.SEMIBOLD
    }

    private fun setShimmer() {
        binding!!.rvShimmer.apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(createItemDecoration(10.dp))
            shimmerAdapter = ShimmerMapTypeListAdapter()
            adapter = shimmerAdapter
        }
    }

    private fun setTypes(types: List<MapTypeDto>) {
        binding!!.rvTypes.apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(createItemDecoration(10.dp))
            typeAdapter = TypeAdapter(types) { mapType ->
                sharedVm.select(mapType)
                pushFragment(MapFragment(), "map")

            }
            adapter = typeAdapter
        }
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                types.collectLatest { result ->
                    when (result) {
                        is ServerResponse.OK -> {
                            binding!!.rvShimmer.visibility = View.GONE

                                setTypes(result.value!!)

                        }

                        is ServerResponse.Loading -> {}

                        is ServerResponse.Error -> {
                            Log.d(TAG, result.errorMessage)
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}