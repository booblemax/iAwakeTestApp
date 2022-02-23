package com.example.iawaketestapp.util

import android.content.Context
import com.example.iawaketestapp.R

object TimeUtils {

    private const val SECONDS = 60

    fun formatDuration(context: Context, time: Int): String {
        val minutes = time / SECONDS
        val remainingSeconds = time - (minutes * SECONDS)
        return if (time < 0) context.getString(R.string.duration_unknown)
        else context.getString(R.string.duration_format).format(minutes, remainingSeconds)
    }
}
