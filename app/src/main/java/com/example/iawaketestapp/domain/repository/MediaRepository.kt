package com.example.iawaketestapp.domain.repository

import com.example.iawaketestapp.domain.models.ProgramModel

interface MediaRepository {

    suspend fun loadPrograms(): List<ProgramModel>
}