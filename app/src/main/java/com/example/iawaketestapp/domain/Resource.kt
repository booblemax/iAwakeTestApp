package com.example.iawaketestapp.domain

sealed class Resource<out T> {
    data class Success<T>(val data: T? = null) : Resource<T>()
    data class Error(val exception: Exception? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}