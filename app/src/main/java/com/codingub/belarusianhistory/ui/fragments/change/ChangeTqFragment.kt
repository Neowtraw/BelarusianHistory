package com.codingub.belarusianhistory.ui.fragments.change

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentChangeItemsBinding
import com.codingub.belarusianhistory.ui.adapters.change.ChangeTicketAdapter
import com.codingub.belarusianhistory.ui.adapters.change.ChangeTicketQuestionAdapter
import com.codingub.belarusianhistory.ui.adapters.change.ChangeTicketQuestionDto
import com.codingub.belarusianhistory.ui.adapters.change.DeleteType
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.viewmodels.change.ChangeTqViewModel
import com.codingub.belarusianhistory.ui.viewmodels.change.DeletedUiState
import com.codingub.belarusianhistory.ui.viewmodels.change.InsertedUiState
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.serializable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangeTqFragment : BaseFragment() {

    private lateinit var binding: FragmentChangeItemsBinding
    private var tqAdapter: ChangeTicketQuestionAdapter? = null
    private val vm by viewModels<ChangeTqViewModel>()

    private var ticketId: String? = null

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentChangeItemsBinding.inflate(inf, con, false)

        ticketId = (requireArguments().serializable("ticketId") as? String)!!

        // get all tqs from ticket
        vm.getTqs(ticketId!!)

        createTexts()
        createTqList()
        selectListeners()
        observeChanges()
        return binding.root
    }

    private fun createTexts() {
        with(binding) {
            tvHeader.typeface = Font.EXTRABOLD
        }
    }

    private fun createTqList() {
        binding.rvContent.apply {
            tqAdapter = ChangeTicketQuestionAdapter(
                onSaveClicked = { tq, _ ->
                    vm.saveChanges(
                        ticketId = ticketId ?: return@ChangeTicketQuestionAdapter,
                        tq = tq.item)
                },
                onGoToSelected = { id ->
                    val fragment = ChangeTqFragment().also {
                        it.arguments = Bundle().apply {
                            putSerializable("ticketId", id)
                        }
                    }

                    pushFragment(fragment, "change_tq")
                })
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tqAdapter
            addItemDecoration(createItemDecoration(10.dp))
        }

    }

    private fun selectListeners() {
        with(binding) {

            // plus
            plus.setOnClickListener {
                addNewElement()
            }

            // delete
            delete.setOnClickListener {
                vm.isRemoved = !vm.isRemoved.also { bool ->
                    if (bool) unselectAllElements()
                }
                tqAdapter?.isRemoving = vm.isRemoved
                delete.isSelected = !delete.isSelected
                checkmark.visibility =
                    if (checkmark.visibility == View.VISIBLE) View.INVISIBLE
                    else View.VISIBLE

                if (delete.drawable is StateListDrawable) {
                    val state = delete.drawable.current as? AnimatedVectorDrawable
                    state?.start()
                }

            }

            // checkmark
            checkmark.setOnClickListener { deleteElements() }

        }
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                tqs.collect {
                    when (it) {
                        is ServerResponse.OK -> {
                            val data = it.value?.toMutableList() ?: mutableListOf()
                            tqAdapter!!.items =
                                data.map { tick ->
                                    ChangeTicketQuestionDto(
                                        tick,
                                        DeleteType.REMOTE
                                    )
                                }
                                    .toMutableList()
                            tqAdapter!!.notifyItemRangeChanged(
                                0,
                                tqAdapter!!.items!!.size
                            )
                            Log.d("dd",data.size.toString())
                        }

                        is ServerResponse.Loading -> {
                            Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                        }

                        is ServerResponse.Error -> {
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                        else -> {}
                    }
                }
            }
            lifecycleScope.launch {
                isTqInserted.collectLatest {
                    when (it) {
                        is InsertedUiState.Loading -> {
                            Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                        }

                        is InsertedUiState.Inserted -> {
                            Toast.makeText(
                                requireContext(),
                                "Successfully inserted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is InsertedUiState.Failed -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            lifecycleScope.launch {
                isTqDeleted.collectLatest {
                    when (it) {
                        is DeletedUiState.Loading -> {
                            Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                        }

                        is DeletedUiState.Deleted -> {
                            vm.isRemoved = false
                            tqAdapter?.isRemoving = false
                            binding.checkmark.visibility = View.INVISIBLE

                            if (binding.delete.drawable is StateListDrawable) {
                                binding.delete.isSelected = false
                                val state =
                                    binding.delete.drawable.current as? AnimatedVectorDrawable
                                state?.start()
                            }
                        }

                        is DeletedUiState.Failed -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    /*
        Additional
     */

    private fun unselectAllElements() {
        val itemCount = tqAdapter?.itemCount ?: 0

        for (i in 0 until itemCount) unselectElement(position = i)
        tqAdapter?.selectedItems?.clear()
    }

    private fun unselectElement(position: Int) {
        binding.rvContent.post {
            val holder =
                binding.rvContent.findViewHolderForAdapterPosition(position)
            if (holder != null) {
                val vh = holder as ChangeTicketQuestionAdapter.TicketQuestionsViewHolder
                vh.binding.root.isSelected = false
            }
        }
    }

    private fun addNewElement() {
        val tickets = tqAdapter?.selectedItems!!
        val locale = tickets.filter { it.deleteType == DeleteType.LOCAL }
        if (locale.isEmpty()) {
            tqAdapter?.addItem(
                ChangeTicketQuestionDto(
                    TicketQuestionDto(
                        name = "",
                        info = "",
                        practices = emptyList(),
                        achieve = null
                    ), DeleteType.LOCAL
                )
            )
            unselectElement(tqAdapter?.itemCount?.minus(1) ?: return)
            return
        }

        Toast.makeText(requireContext(), "Вы уже создали новый билет", Toast.LENGTH_SHORT).show()
    }

    private fun deleteElements() {
        vm.setDeleteState(DeletedUiState.Loading)
        val items = tqAdapter?.selectedItems!!

        val locale = items.filter { it.deleteType == DeleteType.LOCAL }
        val remote = items.filter { it.deleteType == DeleteType.REMOTE }

        when {
            locale.isNotEmpty() && remote.isEmpty() -> {
                tqAdapter?.removeItems(locale)
                vm.setDeleteState(DeletedUiState.Deleted)
            }

            remote.isNotEmpty() -> {
                vm.deleteItems(remote.map { it.item })
                tqAdapter?.removeItems(remote)
            }
        }

        tqAdapter?.selectedItems?.clear()
    }
}