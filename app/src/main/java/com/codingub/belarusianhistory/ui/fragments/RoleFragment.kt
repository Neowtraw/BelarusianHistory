package com.codingub.belarusianhistory.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.data.local.prefs.UserConfig
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentRoleBinding
import com.codingub.belarusianhistory.ui.adapters.RoleAdapter
import com.codingub.belarusianhistory.ui.viewmodels.RoleViewModel
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ItemDecoration
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoleFragment : BaseFragment() {

    private lateinit var binding: FragmentRoleBinding
    private lateinit var roleAdapter: RoleAdapter
    private val vm: RoleViewModel by viewModels()

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentRoleBinding.inflate(inf, con, false)

        createUI()
        observeChanges()
        return binding.root
    }

    private fun createUI() {
        binding.tvChooseRole.typeface = Font.EXTRABOLD
        binding.rvRoles.apply {
            layoutManager = LinearLayoutManager(context)
            overScrollMode = View.OVER_SCROLL_NEVER
            roleAdapter = RoleAdapter() {
                Log.d("",it.name)
                UserConfig.setAccessLevel(it)
                vm.changeRole(accessLevel = it)
            }
            adapter = roleAdapter
            addItemDecoration(ItemDecoration.createLinBottomItemDecoration(9.dp))
        }
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    roleState.collectLatest {
                        when (it) {
                            is ServerResponse.Loading -> { // загрузка
                                /**
                                 * ЗДЕСЬ ТВОЙ КОД
                                 */
                            }
                            is ServerResponse.OK -> {
                                pushFragment(MenuFragment(), "menu")
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
}