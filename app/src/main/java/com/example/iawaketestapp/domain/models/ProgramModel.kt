package com.example.iawaketestapp.domain.models

data class ProgramModel(
    val id: String = "",
    val title: String = "",
    val cover: CoverModel = CoverModel(),
    val descriptionHTML: String = "",
    val tracks: List<TrackModel> = listOf(),
)
