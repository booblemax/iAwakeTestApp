package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class Resolution(
    @SerializedName("size")
    val size: Int? = 0,
    @SerializedName("_type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = ""
)