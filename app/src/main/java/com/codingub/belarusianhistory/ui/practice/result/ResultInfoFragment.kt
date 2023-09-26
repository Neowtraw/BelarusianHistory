package com.codingub.belarusianhistory.ui.practice.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.codingub.belarusianhistory.databinding.FragmentResultInfoBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.menu.MenuFragment
import com.codingub.belarusianhistory.utils.Font
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResultInfoFragment : BaseFragment(){


    private lateinit var binding: FragmentResultInfoBinding

    override fun create() {
        super.create()

//        setFragmentResultListener("result") { _, bundle ->
//            val list = bundle.getStringArrayList("resultList")
//            if (list != null) {
//                for (item in list) {
//                    // Обработка каждого элемента списка
//                }
//            }else{
//                pushFragment(MenuFragment(), "menu")
//            }
//        }

    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentResultInfoBinding.inflate(inf, con, false)

        createTexts()

        return binding.root
    }

    private fun createTexts(){

        binding.tvHeader.apply {
            typeface = Font.EXTRABOLD
        }
        binding.tvResult.apply {
            typeface = Font.EXTRABOLD
        }

    }


}