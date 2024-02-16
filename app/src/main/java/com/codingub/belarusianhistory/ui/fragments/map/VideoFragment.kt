package com.codingub.belarusianhistory.ui.fragments.map

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.media3.exoplayer.ExoPlayer
import com.codingub.belarusianhistory.databinding.FragmentVideoBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.viewmodels.map.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : BaseFragment() {

    private var binding: FragmentVideoBinding? = null
    private val vm: VideoViewModel by viewModels()

    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L

    private fun initializePlayer(uri: String) {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding?.videoView?.player = exoPlayer
                val mediaItem = androidx.media3.common.MediaItem.fromUri(uri)
                exoPlayer.setMediaItems(listOf(mediaItem), mediaItemIndex, playbackPosition)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.prepare()
            }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.contentPosition
            mediaItemIndex = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentVideoBinding.inflate(inf, con, false)
        vm.setUri(arguments?.getString("video")!!)
        return binding!!.root
    }

    override fun onStart() {
        super.onStart()
        initializePlayer(vm.uri.value!!)
    }

    override fun onStop() {
        super.onStop()
        if (player == null) {
            releasePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}