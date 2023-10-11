package com.codingub.belarusianhistory.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentLoginBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.Font

class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inf, con, false)

        createUI()

        return binding.root
    }

    private fun createUI() {
        binding.btnLogin.typeface = Font.EXTRABOLD
        binding.tvAppName.typeface = Font.EXTRABOLD
        binding.tvAppInfo.typeface = Font.REGULAR
        binding.tvError.typeface = Font.REGULAR
        binding.tvTransition.typeface = Font.LIGHT
        binding.tvAuthInfo.typeface = Font.LIGHT
        binding.etLogin.typeface = Font.REGULAR
        binding.etPassword.typeface = Font.REGULAR

    }
}