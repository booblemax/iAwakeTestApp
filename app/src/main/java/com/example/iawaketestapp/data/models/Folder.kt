package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class Folder(
    @SerializedName("cover")
    val cover: Cover? = Cover(),
    @SerializedName("descriptionHTML")
    val descriptionHTML: String? = "",
    @SerializedName("folders")
    val folders: List<String>? = listOf(),
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("_type")
    val type: String? = ""
)