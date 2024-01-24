package com.codingub.belarusianhistory.ui.fragments.map

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.models.map.MapTypeDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentInteractiveMapBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.viewmodels.map.MapViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.serializable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MapFragment : BaseFragment() {

    private lateinit var binding: FragmentInteractiveMapBinding
    private val vm by viewModels<MapViewModel>()
    private val sharedVm by activityViewModels<SharedViewModel>()

    private val labels: List<ImageView> = emptyList()

    override fun create() {
        super.create()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentInteractiveMapBinding.inflate(inf, con, false)



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
            vm.onLabelAddedEvent(MapViewModel.LabelAddedEvent.OnItemSelected(!vm.addedState.value.isItemAdded))
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
//                binding.label.x = event.x
//                binding.label.y = event.y
            }
        }
        return true
    }


    override fun observeChanges() {
        lifecycleScope.launch {
            sharedVm.mapType.collectLatest {
                vm.getMap(it!!.periods.first().id)
            }
        }
        with(vm) {
            lifecycleScope.launch {
                map.collectLatest { result ->
                    when(result) {
                        is ServerResponse.OK -> {
                            binding.loading.visibility = View.GONE
                            withContext(Dispatchers.Main) {
                                activity?.let {
                                    Glide.with(it.applicationContext)
                                        .load(result.value?.map)
                                        .override(4000, 3000)
                                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                                        .into(binding.interactiveMap)
                                }

                                for(label in result.value?.labels!!) {
                                    val imageView = ImageView(context).apply {
                                        setImageResource(R.drawable.ic_marker)
                                        x = label.x
                                        y = label.y
                                        layoutParams = ViewGroup.LayoutParams(100.dp, 100.dp)
                                        setOnClickListener {
                                            vm.onLabelUpdatedEvent(MapViewModel.LabelUpdatedEvent.OnLabelInfoShowed(!vm.updatedState.value.isLabelInfoShowed))
                                            if (vm.updatedState.value.isLabelInfoShowed) {
                                                binding.tvHeaderInfo.text = label.title
                                                binding.tvDescriptionInfo.text = label.description
                                                activity?.let {
                                                    Glide.with(it.applicationContext)
                                                        .load(label.image)
                                                        .into(binding.imgInfo)
                                                }
                                                show(binding.labelInfo)
                                                return@setOnClickListener
                                            }
                                            hide(binding.labelInfo)
                                        }
                                    }
                                    binding.labelContainer.addView(imageView)
                                }
                            }
                        }
                        is ServerResponse.Error -> {
                            binding.loading.visibility = View.GONE

                        }
                        is ServerResponse.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                        }
                        else -> {}
                    }
                }
            }
            lifecycleScope.launch {
                addedState.collectLatest { result ->
                    when(result.response) {
                        is ServerResponse.OK -> {
                            binding.loading.visibility = View.GONE
                        }
                        is ServerResponse.Error -> {
                            binding.loading.visibility = View.GONE

                        }
                        is ServerResponse.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                        }
                        else -> {}
                    }

                }
            }

            lifecycleScope.launch {
                updatedState.collectLatest { result ->
                    when(result.response) {
                        is ServerResponse.OK -> {
                            binding.loading.visibility = View.GONE

                        }
                        is ServerResponse.Error -> {
                            binding.loading.visibility = View.GONE

                        }
                        is ServerResponse.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                        }
                        else -> {}
                    }
                }
            }

            lifecycleScope.launch {
                deletedState.collectLatest { result ->
                    when(result.response) {
                        is ServerResponse.OK -> {
                            binding.loading.visibility = View.GONE
                        }
                        is ServerResponse.Error -> {
                            binding.loading.visibility = View.GONE
                        }
                        is ServerResponse.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    /*
        Additional
     */
    private fun show(view: View) {
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

    private fun hide(view: View) {
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