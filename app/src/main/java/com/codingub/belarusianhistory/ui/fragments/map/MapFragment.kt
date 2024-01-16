package com.codingub.belarusianhistory.ui.fragments.map

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.FragmentInteractiveMapBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.viewmodels.MapViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : BaseFragment() {

    private lateinit var binding: FragmentInteractiveMapBinding
    private val vm by viewModels<MapViewModel>()

    override fun create() {
        super.create()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentInteractiveMapBinding.inflate(inf, con, false)


        activity?.let {
            Glide.with(it.applicationContext)
                .load(R.drawable.interactive_map_test)
                .override(4000, 3000)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(binding.interactiveMap)
        }

        setGeneralUI()
        observeChanges()
        setupListeners()
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

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        binding.btnAdd.setOnClickListener {
            vm.setLabelAdded(!vm.addedState.value.isItemAdded)
        }

        binding.label.setOnClickListener {
            vm.setInfoShowed(!vm.isInfoShowed.value)
            if(vm.isInfoShowed.value) {
                show(binding.labelInfo)
                return@setOnClickListener
            }
            hide(binding.labelInfo)
        }

        binding.interactiveMap.setOnTouchListener { _, event ->
            if (vm.addedState.value.isItemAdded)
                handleAddLabelTouch(event)
            true
        }
    }

    private fun handleAddLabelTouch(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_UP -> {
                binding.label.x = event.x
                binding.label.y = event.y
            }
        }
        return true
    }


    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                addedState.collectLatest {


                }
            }

            lifecycleScope.launch {
                isInfoShowed.collectLatest {

                }
            }
        }
    }

    /*
        Additional
     */
    fun show(view: View) {
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 1.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f
        ).apply {
            duration = 500L
            fillAfter = true
        }
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                view.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        view.startAnimation(animation)
    }

    fun hide(view: View) {
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 1.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f
        ).apply {
            duration = 500L
            fillAfter = true
        }
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                view.visibility = View.INVISIBLE
            }
        })

        view.startAnimation(animation)
    }
}