package com.example.iawaketestapp.domain.models

data class TrackModel(
    val key: String = "",
    val title: String = "",
    val duration: Int = 0,
    val order: Int = 0,
    val type: String = "",
    val media: TrackMediaModel = TrackMediaModel()
)
