package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class Cover(
    @SerializedName("resolutions")
    val resolutions: List<Resolution>? = listOf(),
    @SerializedName("thumbnail")
    val thumbnail: String? = "",
    @SerializedName("_type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = ""
)