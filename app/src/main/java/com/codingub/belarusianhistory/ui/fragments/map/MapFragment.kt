package com.codingub.belarusianhistory.ui.fragments.map

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingub.belarusianhistory.databinding.FragmentInteractiveMapBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.Font

class MapFragment : BaseFragment() {

    private lateinit var binding: FragmentInteractiveMapBinding

    override fun create() {
        super.create()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentInteractiveMapBinding.inflate(inf, con, false)

        setGeneralUI()
        setupListeners()
        setupZoom()
        return binding.root
    }

    override fun destroyView() {
        super.destroyView()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun setGeneralUI() {
        binding.btnPlayAnimation.typeface = Font.REGULAR
        binding.tvHeaderInfo.typeface = Font.SEMIBOLD
        binding.tvDescriptionInfo.typeface = Font.REGULAR
    }

    private fun createSeekbar() {

    }

    private fun setupListeners() {

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupZoom() {

    }
}