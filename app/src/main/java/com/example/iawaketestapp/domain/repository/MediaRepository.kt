package com.example.iawaketestapp.domain.repository

import com.example.iawaketestapp.domain.models.ProgramModel
import com.example.iawaketestapp.domain.models.TrackModel

interface MediaRepository {

    suspend fun loadPrograms(): List<ProgramModel>

    suspend fun getTracksForProgram(id: String): List<TrackModel>
}