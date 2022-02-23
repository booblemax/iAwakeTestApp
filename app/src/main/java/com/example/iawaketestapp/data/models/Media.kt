package com.example.iawaketestapp.data.models


import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("flac")
    val flac: Flac? = Flac(),
    @SerializedName("m4a")
    val m4a: M4a? = M4a(),
    @SerializedName("mp3")
    val mp3: Mp3? = Mp3(),
    @SerializedName("_type")
    val type: String? = ""
)