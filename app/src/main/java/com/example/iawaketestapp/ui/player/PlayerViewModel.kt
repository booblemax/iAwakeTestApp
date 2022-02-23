package com.example.iawaketestapp.ui.player

import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iawaketestapp.ui.base.MusicStateManager
import com.example.iawaketestapp.ui.base.PlaybackCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val musicStateManager: MusicStateManager
) : ViewModel() {

    private var playbackCallback: PlaybackCallback? = null
    val playState = musicStateManager.playingState
    val playingSong = musicStateManager.playingSong
    val progress = musicStateManager.playingProgress

    fun setPlaybackCallback(playbackCallback: PlaybackCallback) {
        this.playbackCallback = playbackCallback
    }

    fun onPlayPauseClicked() {
        viewModelScope.launch {
            if (musicStateManager.playingState.value == PlaybackStateCompat.STATE_PLAYING) {
                musicStateManager.updatePlayingState(PlaybackStateCompat.STATE_PAUSED)
                playbackCallback?.pause()
            } else {
                musicStateManager.updatePlayingState(PlaybackStateCompat.STATE_PLAYING)
                playbackCallback?.resume()
            }
        }
    }

    fun updateProgress() {
        val progress = playbackCallback?.getCurrentProgress() ?: 0
        viewModelScope.launch {
            musicStateManager.updatePlayingProgress(progress)
        }
    }

    fun clear() {
        playbackCallback = null
    }
}