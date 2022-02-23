package com.example.iawaketestapp.data.repository

import com.example.iawaketestapp.data.models.toModels
import com.example.iawaketestapp.data.service.MediaService
import com.example.iawaketestapp.domain.models.ProgramModel
import com.example.iawaketestapp.domain.models.TrackModel
import com.example.iawaketestapp.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val service: MediaService
) : MediaRepository {

    private var cache: List<ProgramModel> = emptyList()

    override suspend fun loadPrograms(): List<ProgramModel> {
        if (cache.isEmpty()) {
            cache = service.loadMedia().programs?.toModels() ?: emptyList()
        }
        return cache
    }

    override suspend fun getTracksForProgram(id: String): List<TrackModel> =
        cache.firstOrNull { it.id == id }?.tracks ?: emptyList()
}