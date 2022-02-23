package com.example.iawaketestapp.data.models

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("thumbnail")
    val thumbnail: String? = "",
    @SerializedName("_type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = ""
)