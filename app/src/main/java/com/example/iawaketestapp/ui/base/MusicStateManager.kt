package com.example.iawaketestapp.ui.base

import com.example.iawaketestapp.domain.models.TrackModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class MusicStateManager @Inject constructor() {

    private val mPlayingSong = MutableSharedFlow<TrackModel>()
    val playingSong: SharedFlow<TrackModel> = mPlayingSong

    private val mPlayingState = MutableSharedFlow<Int>()
    val playingState: SharedFlow<Int> = mPlayingState

    suspend fun updatePlayingSong(newSong: TrackModel) {
        mPlayingSong.emit(newSong)
    }

    suspend fun updatePlayingState(state: Int) {
        mPlayingState.emit(state)
    }
}