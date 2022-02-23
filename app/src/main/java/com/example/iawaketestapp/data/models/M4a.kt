package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class M4a(
    @SerializedName("headUrl")
    val headUrl: String? = "",
    @SerializedName("_type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = ""
)