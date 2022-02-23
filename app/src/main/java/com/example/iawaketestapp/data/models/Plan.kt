package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class Plan(
    @SerializedName("endDate")
    val endDate: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("startDate")
    val startDate: String? = ""
)