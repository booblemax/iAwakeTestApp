package com.example.iawaketestapp.data.repository

import com.example.iawaketestapp.data.models.toModels
import com.example.iawaketestapp.data.service.MediaService
import com.example.iawaketestapp.domain.models.ProgramModel
import com.example.iawaketestapp.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val service: MediaService
) : MediaRepository {

    override suspend fun loadPrograms(): List<ProgramModel> {
        val mediaResponse = service.loadMedia()
        return mediaResponse.programs?.toModels() ?: emptyList()
    }
}