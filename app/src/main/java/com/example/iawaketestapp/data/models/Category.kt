package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("cover")
    val cover: Cover? = Cover(),
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("subtitle")
    val subtitle: String? = "",
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("_type")
    val type: String? = ""
)