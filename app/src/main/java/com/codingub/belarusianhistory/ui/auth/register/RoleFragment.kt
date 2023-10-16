package com.codingub.belarusianhistory.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentRegisterBinding
import com.codingub.belarusianhistory.databinding.FragmentRoleBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.Font

class RoleFragment : BaseFragment(){

    private lateinit var binding: FragmentRoleBinding

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentRoleBinding.inflate(inf, con, false)

        createUI()

        return binding.root
    }

    private fun createUI(){
        binding.tvChooseRole.typeface = Font.EXTRABOLD
    }
}