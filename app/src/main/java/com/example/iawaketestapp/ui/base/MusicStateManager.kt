package com.example.iawaketestapp.ui.base

import com.example.iawaketestapp.domain.models.TrackModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MusicStateManager @Inject constructor() {

    private val mPlayingSong = MutableStateFlow<TrackModel>(TrackModel())
    val playingSong: StateFlow<TrackModel> = mPlayingSong

    private val mPlayingState = MutableStateFlow<Int>(-1)
    val playingState: StateFlow<Int> = mPlayingState

    private val mPlayingProgress = MutableStateFlow<Int>(-1)
    val playingProgress: StateFlow<Int> = mPlayingProgress

    suspend fun updatePlayingSong(newSong: TrackModel) {
        mPlayingSong.emit(newSong)
    }

    suspend fun updatePlayingState(state: Int) {
        mPlayingState.emit(state)
    }

    suspend fun updatePlayingProgress(progress: Int) {
        mPlayingProgress.emit(progress)
    }
}