package com.example.iawaketestapp.data.service

import com.example.iawaketestapp.data.models.MediaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaService {

    @GET("api/v2/media-library/free")
    suspend fun loadMedia(@Query("resetCache") resetCache: Boolean = false): MediaResponse
}