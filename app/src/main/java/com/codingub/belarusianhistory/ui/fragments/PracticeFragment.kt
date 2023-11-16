package com.codingub.belarusianhistory.ui.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.tickets.TicketQuestion
import com.codingub.belarusianhistory.databinding.FragmentPracticeBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.adapters.PracticeAdapter
import com.codingub.belarusianhistory.ui.viewmodels.PracticeViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PracticeFragment : BaseFragment() {

    private val model: SharedViewModel  by activityViewModels()
    private val vm : PracticeViewModel by viewModels()

    private lateinit var binding: FragmentPracticeBinding
    private lateinit var practiceAdapter: PracticeAdapter

    private val practiceList = mutableListOf<TicketQuestion>()


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentPracticeBinding.inflate(inf, con, false)

        createUI()
        observeChanges()
        return binding.root
    }

    private fun createUI(){
        binding.tvHeader.typeface = Font.EXTRABOLD
        binding.tvEmptyPractice.typeface = Font.EXTRABOLD

        binding.rvPractice.apply {
            layoutManager = LinearLayoutManager(requireContext())
            practiceAdapter = PracticeAdapter(practiceList){ practice ->
                model.select(practice)
                pushFragment(PracticeInfoFragment(), "practiceInfo")
            }
            adapter = practiceAdapter
            addItemDecoration(createItemDecoration(6.dp))
        }
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    practiceState.collectLatest {
                        when (it) {
                            is ServerResponse.Loading -> {
                                /**
                                 * ЗДЕСЬ ТВОЙ КОД
                                 */
                            }
                            is ServerResponse.OK -> {
                                if(it.value.isNullOrEmpty()){
                                    binding.tvEmptyPractice.visibility = View.VISIBLE
                                } else{
                                    binding.rvPractice.visibility = View.VISIBLE
                                    practiceList.clear()
                                    practiceList.addAll(it.value)
                                    practiceAdapter.notifyItemRangeChanged(0,practiceAdapter.itemCount)
                                }
                            }
                            is ServerResponse.Error -> {
                                Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    /*
        Additional
     */

    private fun createItemDecoration(spacing: Int): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)

                outRect.bottom = spacing
                if(position % 3 == 0 && position != 0){
                    outRect.top = spacing * 4
                }
            }
        }
    }
}