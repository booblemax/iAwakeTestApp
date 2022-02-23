package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class MediaResponse(
    @SerializedName("categories")
    val categories: List<Category>? = listOf(),
    @SerializedName("plan")
    val plan: Plan? = Plan(),
    @SerializedName("playlists")
    val playlists: List<Playlists>? = listOf(),
    @SerializedName("programs")
    val programs: List<Program>? = listOf(),
    @SerializedName("_type")
    val type: String? = ""
)