package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("duration")
    val duration: Int? = 0,
    @SerializedName("folderPath")
    val folderPath: List<List<String>>? = listOf(),
    @SerializedName("isAvailable")
    val isAvailable: Boolean? = false,
    @SerializedName("key")
    val key: String? = "",
    @SerializedName("media")
    val media: Media? = Media(),
    @SerializedName("order")
    val order: Int? = 0,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("_type")
    val type: String? = ""
)