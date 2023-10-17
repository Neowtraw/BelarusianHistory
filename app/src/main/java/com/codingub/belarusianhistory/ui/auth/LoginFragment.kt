package com.codingub.belarusianhistory.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.codingub.belarusianhistory.data.remote.network.DataUiResult
import com.codingub.belarusianhistory.databinding.FragmentLoginBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.Font
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private val vm : LoginViewModel by viewModels()

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inf, con, false)

        createUI()

        observeChanges()
        return binding.root
    }

    private fun createUI() {
        binding.tvAppName.typeface = Font.EXTRABOLD
        binding.tvAppInfo.typeface = Font.REGULAR
        binding.tvError.typeface = Font.REGULAR
        binding.tvTransition.typeface = Font.LIGHT
        binding.tvAuthInfo.typeface = Font.LIGHT

        binding.etLogin.apply{
            typeface = Font.REGULAR
            addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    vm.onEvent(AuthUiEvent.SignInLoginChanged(s.toString()))
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun afterTextChanged(s: Editable?) = Unit
            })
        }

        binding.etPassword.apply{
            typeface = Font.REGULAR
            addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    vm.onEvent(AuthUiEvent.SignInPasswordChanged(s.toString()))
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun afterTextChanged(s: Editable?) = Unit
            })
        }
        binding.btnLogin.apply {
            typeface = Font.EXTRABOLD
            vm.onEvent(AuthUiEvent.SignIn)
        }


    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    authResults.collectLatest {
                        when (it) {
                            is DataUiResult.Success -> {
                                pushToMenu()
                            }
                            is DataUiResult.Loading -> {}
                            is DataUiResult.Error -> {
                                binding.tvError.text = "Пожалуйста повторите попытку позже"
                            }
                        }
                    }
                }
            }
        }
    }
}