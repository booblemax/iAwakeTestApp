package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class Playlists(
    @SerializedName("cover")
    val cover: Cover? = Cover(),
    @SerializedName("descriptionHTML")
    val descriptionHTML: String? = "",
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("order")
    val order: Int? = 0,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("_type")
    val type: String? = ""
)