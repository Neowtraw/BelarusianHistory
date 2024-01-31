package com.codingub.belarusianhistory.ui.fragments.map

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentInteractiveMapBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.viewmodels.map.MapViewModel
import com.codingub.belarusianhistory.ui.viewmodels.map.MenuEvent
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MapFragment : BaseFragment() {

    private var binding: FragmentInteractiveMapBinding? = null
    private val vm by viewModels<MapViewModel>()
    private val sharedVm by activityViewModels<SharedViewModel>()
    private var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null

    override fun create() {
        super.create()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentInteractiveMapBinding.inflate(inf, con, false)

        setupPickMedia()

        setGeneralUI()
        observeChanges()
        setupListeners()
        return binding?.root!!
    }

    override fun destroyView() {
        super.destroyView()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun setGeneralUI() {
        binding?.flAnimation?.typeface = Font.REGULAR
        binding?.title?.typeface = Font.SEMIBOLD
        binding?.description?.typeface = Font.REGULAR
        binding?.adAddLabel?.title?.typeface = Font.SEMIBOLD
        binding?.adAddLabel?.description?.typeface = Font.REGULAR

        binding?.adAddLabel?.labelInfo?.visibility = View.GONE
    }

    private fun setupPickMedia() {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri == null) {
                binding?.adAddLabel?.flImage?.isActivated = false
                binding?.adAddLabel?.flAnimation?.isActivated = false
                return@registerForActivityResult
            }
            when {
                binding?.adAddLabel?.flImage?.isActivated == true -> {
                    vm.onLabelAddedEvent(MapViewModel.LabelAddedEvent.OnSetImage(uri.toString()))
                    binding?.adAddLabel?.flImage?.isActivated = false
                }

                binding?.adAddLabel?.flAnimation?.isActivated == true -> {
                    vm.onLabelAddedEvent(MapViewModel.LabelAddedEvent.OnSetAnimation(uri.toString()))
                    binding?.adAddLabel?.flAnimation?.isActivated = false

                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        binding?.btnAdd?.setOnClickListener {
            changeAddBtnState(!vm.isAddedAvailable.value)
        }

        binding?.interactiveMap?.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.rawX.toInt() - binding?.adAddLabel?.labelInfo!!.left
                val y = event.rawY.toInt() - binding?.adAddLabel?.labelInfo!!.top

                if (!isPointInsideView(
                        x,
                        y,
                        binding?.adAddLabel?.labelInfo!!
                    ) && binding?.adAddLabel?.labelInfo!!.isVisible
                ) {
                    AnimationUtils.loadAnimation(requireContext(), R.anim.dialog_out).also {
                        it.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationEnd(animation: Animation?) {
                                binding?.adAddLabel?.labelInfo?.visibility = View.GONE
                                changeAddBtnState(false)
                            }

                            override fun onAnimationStart(animation: Animation?) {}
                            override fun onAnimationRepeat(animation: Animation?) {}
                        })
                        binding?.adAddLabel?.labelInfo?.startAnimation(it)
                    }
                }
            }

            if (vm.isAddedAvailable.value)
                showAddedAlertDialog(event.x, event.y)
            true
        }

        binding?.adAddLabel?.flImage?.setOnClickListener {
            binding?.adAddLabel?.flImage?.isActivated = true
            pickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding?.adAddLabel?.flAnimation?.setOnClickListener {
            binding?.adAddLabel?.flAnimation?.isActivated = true
            pickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        }
        binding?.adAddLabel!!.title.doOnTextChanged { text, start, before, count ->
            vm.onLabelAddedEvent(
                MapViewModel.LabelAddedEvent.OnTitleSet(if (text.isNullOrBlank()) null else text.toString())
            )
        }
        binding?.adAddLabel!!.description.doOnTextChanged { text, start, before, count ->
            vm.onLabelAddedEvent(
                MapViewModel.LabelAddedEvent.OnDescriptionSet(if (text.isNullOrBlank()) null else text.toString())
            )
        }
        binding?.adAddLabel?.btnAccept!!.setOnClickListener {
            Log.d("", "и здемь")
            vm.onEvent(MenuEvent.LabelAdded)
        }
    }

    private fun showAddedAlertDialog(x: Float, y: Float) {
        binding?.adAddLabel?.labelInfo!!.visibility = View.VISIBLE
        vm.onLabelAddedEvent(MapViewModel.LabelAddedEvent.OnSetCoordinates(x,y))
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
                    when (result) {
                        is ServerResponse.OK -> {
                            binding?.loading?.visibility = View.GONE
                            withContext(Dispatchers.Main) {
                                activity?.let {
                                    Glide.with(requireContext().applicationContext)
                                        .load(result.value?.map)
                                        .override(4000, 3000)
                                        .into(binding?.interactiveMap!!)
                                }

                                for (label in result.value?.labels!!) {
                                    val imageView = ImageView(context).apply {
                                        setImageResource(R.drawable.ic_marker)
                                        x = label.x
                                        y = label.y
                                        layoutParams = ViewGroup.LayoutParams(100.dp, 100.dp)
                                        setOnClickListener {
                                            vm.changeUpdatedAvailable(!vm.isUpdatedAvailable.value)
                                            if (vm.isUpdatedAvailable.value) {
                                                binding?.title?.text = label.title
                                                binding?.description?.text =
                                                    label.description
                                                activity?.let {
                                                    Glide.with(requireContext().applicationContext)
                                                        .load(label.image)
                                                        .into(binding?.image!!)
                                                }
                                                show(binding?.labelInfo!!)
                                                return@setOnClickListener
                                            }
                                            hide(binding?.labelInfo!!)
                                        }
                                    }
                                    binding?.labelContainer?.addView(imageView)
                                }
                            }
                        }

                        is ServerResponse.Error -> {
                            binding?.loading?.visibility = View.GONE

                        }

                        is ServerResponse.Loading -> {
                            binding?.loading?.visibility = View.VISIBLE
                        }

                        else -> {}
                    }
                }
            }
            lifecycleScope.launch {
                addedState.collectLatest { result ->
                    if (result.image != null) {
                        Glide.with(requireContext().applicationContext)
                            .load(result.image)
                            .into(binding?.adAddLabel!!.image)
                    }

                    if (result.animation != null) {
                        Glide.with(requireContext().applicationContext)
                            .load(result.animation)
                            .apply(RequestOptions())
                            .thumbnail(
                                Glide.with(requireContext().applicationContext)
                                    .load(result.animation)
                            )
                            .into(binding?.adAddLabel!!.animation)
                    }

                    if (result.response != null) {
                        when (result.response) {
                            is ServerResponse.OK -> {
                                binding?.loading?.visibility = View.GONE
                                vm.getMap(sharedVm.mapType.value!!.periods.first().id) // temporary
                                changeAddBtnState(false)
                            }

                            is ServerResponse.Error -> {
                                binding?.loading?.visibility = View.GONE

                            }

                            is ServerResponse.Loading -> {
                                binding?.loading?.visibility = View.VISIBLE
                            }

                            else -> {}
                        }
                    }
                }
            }

            lifecycleScope.launch {
                updatedState.collectLatest { result ->
                    when (result.response) {
                        is ServerResponse.OK -> {
                            binding?.loading?.visibility = View.GONE

                        }

                        is ServerResponse.Error -> {
                            binding?.loading?.visibility = View.GONE

                        }

                        is ServerResponse.Loading -> {
                            binding?.loading?.visibility = View.VISIBLE
                        }

                        else -> {}
                    }
                }
            }

            lifecycleScope.launch {
                deletedState.collectLatest { result ->
                    when (result.response) {
                        is ServerResponse.OK -> {
                            binding?.loading?.visibility = View.GONE
                        }

                        is ServerResponse.Error -> {
                            binding?.loading?.visibility = View.GONE
                        }

                        is ServerResponse.Loading -> {
                            binding?.loading?.visibility = View.VISIBLE
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

    private fun changeAddBtnState(state: Boolean) {
        vm.changeAddedAvailable(state)
        val rotateAnimation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_half_anim)

        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) = Unit

            override fun onAnimationEnd(animation: Animation?) {
                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        binding?.btnAdd!!.rotation += 45f
                    }
                    delay(100L)
                }
            }

            override fun onAnimationRepeat(animation: Animation?) = Unit
        })
        binding?.btnAdd?.startAnimation(rotateAnimation)
    }

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

    private fun isPointInsideView(x: Int, y: Int, view: View): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)

        val left = location[0]
        val top = location[1]
        val right = left + view.width
        val bottom = top + view.height

        return x in left..right && y in top..bottom
    }
}