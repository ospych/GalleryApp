package com.example.galleryapp.core.network.utils

sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val message: String?, val errorCode: Int? = null) : ResponseResult<Nothing>()
}