package com.codingub.belarusianhistory.ui.auth.register

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
import com.codingub.belarusianhistory.databinding.FragmentRegisterBinding
import com.codingub.belarusianhistory.ui.auth.AuthResult
import com.codingub.belarusianhistory.ui.auth.AuthUiEvent
import com.codingub.belarusianhistory.ui.auth.login.LoginFragment
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.menu.MenuFragment
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ImageUtil
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
                AssetUtil.menuImageUri("icon")
            ){
                setImageDrawable(it)
            }
        }
        binding.tvError.apply {
            typeface = Font.REGULAR
            text = "Пожалуйста повторите попытку позже"

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
                            is DataUiResult.Success -> {
                                when(it.data){
                                    is AuthResult.Authorized -> {
                                        pushFragment(MenuFragment(), "menu")
                                    }
                                    is AuthResult.Unauthorized -> {
                                        binding.tvError.text = "Вы не авторизованы"
                                    }
                                    is AuthResult.Conflict ->{
                                        binding.tvError.text = it.data.errorMessage
                                    }
                                    is AuthResult.UnknownError ->{
                                        binding.tvError.text = "Повторите попытку позже"
                                    }
                                }
                            }

                            is DataUiResult.Loading -> {
                                binding.tvError.text = "Загрузка"

                            }

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

