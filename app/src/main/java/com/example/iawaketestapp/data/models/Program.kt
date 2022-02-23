package com.example.iawaketestapp.data.models

import com.google.gson.annotations.SerializedName

data class Program(
    @SerializedName("banner")
    val banner: Banner? = Banner(),
    @SerializedName("cover")
    val cover: Cover? = Cover(),
    @SerializedName("descriptionHTML")
    val descriptionHTML: String? = "",
    @SerializedName("folders")
    val folders: List<Folder>? = listOf(),
    @SerializedName("headphones")
    val headphones: Boolean? = false,
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("isAvailable")
    val isAvailable: Boolean? = false,
    @SerializedName("isFeatured")
    val isFeatured: Boolean? = false,
    @SerializedName("isFree")
    val isFree: Boolean? = false,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("tracks")
    val tracks: List<Track>? = listOf(),
    @SerializedName("_type")
    val type: String? = ""
)