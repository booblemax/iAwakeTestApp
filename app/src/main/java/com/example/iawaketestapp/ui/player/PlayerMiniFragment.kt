package com.example.iawaketestapp.ui.player

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.iawaketestapp.R
import com.example.iawaketestapp.databinding.PlayerMiniFragmentBinding
import com.example.iawaketestapp.domain.models.TrackModel
import com.example.iawaketestapp.ui.base.PlaybackCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PlayerMiniFragment : Fragment() {

    private val viewModel: PlayerViewModel by viewModels()
    private var binding: PlayerMiniFragmentBinding? = null

    private var handler: Handler? = null
    private lateinit var pollingRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = PlayerMiniFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
            playPause.setOnClickListener { viewModel.onPlayPauseClicked() }
            progress.disableTouch()
            viewModel.setPlaybackCallback(requireActivity() as PlaybackCallback)
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUpdatingProgress()
        viewModel.playingSong
            .onEach {
                if (it.key.isNotEmpty()) {
                    renderSong(it)
                }
            }.launchIn(lifecycleScope)

        viewModel.playState
            .onEach { state ->
                renderPlayPause(state == PlaybackStateCompat.STATE_PLAYING)
            }.launchIn(lifecycleScope)

        viewModel.progress
            .onEach { state ->
                renderPositionChanging(state)
            }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        viewModel.clear()
        handler?.removeCallbacks(pollingRunnable)
        super.onDestroy()
    }

    private fun setupUpdatingProgress() {
        pollingRunnable = Runnable {
            viewModel.updateProgress()
            handler?.postDelayed(this@PlayerMiniFragment.pollingRunnable, TIME_DELAYED_MILLIS)
        }
        handler = Handler(Looper.getMainLooper())
        handler?.postDelayed(pollingRunnable, TIME_DELAYED_MILLIS)
    }

    private fun renderSong(song: TrackModel) {
        binding?.let {
            it.title.text = song.title
            it.progress.valueFrom = 0
            it.progress.valueTo = song.duration.toLong()
        }
    }

    private fun renderPositionChanging(nextPosition: Int) {
        binding?.progress?.value = nextPosition.toLong()
    }

    private fun renderPlayPause(isPlaying: Boolean) {
        binding?.playPause?.setImageResource(
            if (isPlaying) R.drawable.ic_pause
            else R.drawable.ic_play
        )
    }

    companion object {

        private const val TIME_DELAYED_MILLIS = 500L
        fun newInstance() = PlayerMiniFragment()
    }
}
