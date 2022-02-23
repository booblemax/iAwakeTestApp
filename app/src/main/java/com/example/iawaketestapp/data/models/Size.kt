package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class Size(
    @SerializedName("height")
    val height: Int? = 0,
    @SerializedName("width")
    val width: Int? = 0
)