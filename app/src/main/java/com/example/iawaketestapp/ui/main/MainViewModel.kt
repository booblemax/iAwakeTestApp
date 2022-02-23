package com.example.iawaketestapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iawaketestapp.domain.Resource
import com.example.iawaketestapp.domain.models.ProgramModel
import com.example.iawaketestapp.domain.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val mProgramsState = MutableStateFlow<Resource<List<ProgramModel>>>(Resource.Loading)
    val programsState: StateFlow<Resource<List<ProgramModel>>> = mProgramsState

    fun loadPrograms() {
        viewModelScope.launch {
            mProgramsState.emit(Resource.Loading)
            try {
                val programs = repository.loadPrograms()
                mProgramsState.emit(Resource.Success(programs))
            } catch (e: Exception) {
                Timber.e(e)
                mProgramsState.emit(Resource.Error())
            }
        }
    }
}