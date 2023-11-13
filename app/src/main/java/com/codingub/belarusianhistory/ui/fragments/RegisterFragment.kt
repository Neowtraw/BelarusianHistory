package com.codingub.belarusianhistory.ui.fragments

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
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentRegisterBinding
import com.codingub.belarusianhistory.ui.auth.AuthUiEvent
import com.codingub.belarusianhistory.ui.viewmodels.RegisterViewModel
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ImageUtil
import com.codingub.belarusianhistory.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val vm: RegisterViewModel by viewModels()

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inf, con, false)
        createUI()

        observeChanges()

        return binding.root
    }

    private fun createUI() {
        binding.tvAppName.typeface = Font.EXTRABOLD
        binding.tvAppInfo.typeface = Font.REGULAR
        binding.imgLogo.apply {
            ImageUtil.load(
                AssetUtil.imagesImageUri("icon")
            ) {
                setImageDrawable(it)
            }
        }
        binding.tvError.apply {
            typeface = Font.REGULAR
            text = Resource.string(R.string.error_result)

        }
        binding.tvAuthInfo.typeface = Font.LIGHT

        binding.etName.apply {
            typeface = Font.REGULAR
            addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    vm.onEvent(AuthUiEvent.SignUpUsernameChanged(s.toString()))
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun afterTextChanged(s: Editable?) = Unit
            })
        }

        binding.etLogin.apply {
            typeface = Font.REGULAR
            addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    vm.onEvent(AuthUiEvent.SignUpLoginChanged(s.toString()))
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun afterTextChanged(s: Editable?) = Unit
            })
        }

        binding.etPassword.apply {
            typeface = Font.REGULAR
            addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    vm.onEvent(AuthUiEvent.SignUpPasswordChanged(s.toString()))
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun afterTextChanged(s: Editable?) = Unit
            })
        }

        binding.btnRegister.apply {
            typeface = Font.EXTRABOLD
            setOnClickListener {
                vm.onEvent(AuthUiEvent.SignUp)
            }
        }

        binding.tvTransition.apply {
            typeface = Font.LIGHT
            setOnClickListener {
                pushFragment(LoginFragment(), "login")
            }
        }
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    authResults.collectLatest {
                        when (it) {
                            is ServerResponse.Loading -> {
                                /**
                                 *  ЗДЕСЬ ТВОЙ КОД
                                 */
                            }
                            is ServerResponse.Authorized -> {
                                pushFragment(RoleFragment(), "role")
                            }

                            is ServerResponse.Unauthorized -> {
                                binding.tvError.text = "Вы не авторизованы"
                            }

                            is ServerResponse.Error -> {
                                binding.tvError.text = it.errorMessage
                            }

                            else -> {}
                        }
                    }

                }
            }
        }
    }
}

