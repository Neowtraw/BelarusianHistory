package com.codingub.belarusianhistory.ui.tickets_practice.practice

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
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.tickets.TicketQuestion
import com.codingub.belarusianhistory.databinding.FragmentPracticeBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.practice.PracticeInfoFragment
import com.codingub.belarusianhistory.ui.tickets_practice.MainItemDecorator
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

        with(vm) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    practiceState.collectLatest {
                        when (it) {
                            is ServerResponse.Loading -> { // загрузка
                                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                                /**
                                 * ЗДЕСЬ ТВОЙ КОД
                                 */
                            }
                            is ServerResponse.OK -> {

                                if(it.data.isNullOrEmpty()){
                                    binding.rvPractice.visibility = View.INVISIBLE
                                    binding.tvEmptyPractice.visibility = View.VISIBLE
                                } else{
                                    binding.rvPractice.visibility = View.VISIBLE
                                    binding.tvEmptyPractice.visibility = View.INVISIBLE
                                    practiceList.clear()
                                    practiceList.addAll(it.data)
                                    adapter.notifyDataSetChanged()
                                }

                                Toast.makeText(requireContext(), "Data was loaded", Toast.LENGTH_LONG).show()
                            }
                            is ServerResponse.Conflict -> {
                                Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
                            }
                            is ServerResponse.BadRequest -> {
                                Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
                            }
                            is ServerResponse.NotFound -> {
                                Toast.makeText(requireContext(), "Not found", Toast.LENGTH_LONG).show()
                            }
                            else -> {

                                //will change
                                Toast.makeText(requireContext(), "Connection problems", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }
}