package com.example.iawaketestapp.ui.base

interface PlaybackCallback {

    fun play(url: String)

    fun resume()

    fun pause()

    fun stop()

    fun getCurrentProgress(): Int
}