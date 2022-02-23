package com.example.iawaketestapp.data.models

import com.example.iawaketestapp.domain.models.CoverModel
import com.example.iawaketestapp.domain.models.ProgramModel
import com.example.iawaketestapp.domain.models.TrackMediaModel
import com.example.iawaketestapp.domain.models.TrackModel

fun List<Program>.toModels() = map { it.toModel() }

fun Program.toModel() = ProgramModel(
    id.orEmpty(),
    title.orEmpty(),
    cover?.toModel() ?: CoverModel(),
    descriptionHTML.orEmpty(),
    tracks?.map { it.toModel() } ?: emptyList()
)

fun Cover.toModel() = CoverModel(thumbnail.orEmpty(), url.orEmpty())

fun Track.toModel() = TrackModel(
    key.orEmpty(),
    title.orEmpty(),
    duration ?: 0,
    order ?: -1,
    type.orEmpty(),
    media?.toModel() ?: TrackMediaModel()
)

fun Media.toModel() =
    TrackMediaModel(mp3?.url.orEmpty(), mp3?.type.orEmpty(), mp3?.headUrl.orEmpty())