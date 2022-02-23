package com.example.iawaketestapp.ui.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iawaketestapp.domain.Resource
import com.example.iawaketestapp.domain.models.TrackModel
import com.example.iawaketestapp.domain.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val mTracksState = MutableStateFlow<Resource<List<TrackModel>>>(Resource.Loading)
    val tracksState: StateFlow<Resource<List<TrackModel>>> = mTracksState

    fun getTracksForProgram(id: String) {
        viewModelScope.launch {
            mTracksState.emit(Resource.Loading)
            try {
                val tracks = repository.getTracksForProgram(id)
                mTracksState.emit(Resource.Success(tracks))
            } catch (e: Exception) {
                mTracksState.emit(Resource.Error())
            }
        }
    }
}