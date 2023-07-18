package com.example.galleryapp.core.network.utils

import com.example.galleryapp.core.network.interceptors.NetworkErrorException
import javax.inject.Inject


class RequestHandler @Inject constructor(): IRequestHandler {
    override suspend fun <T> executeRequest(
        request: suspend () -> T,
        successBlock: (T) -> ResponseResult<T>,
        errorBlock: (String?, Int?) -> ResponseResult<T>
    ): ResponseResult<T> {
        return try {
            val response = request()
            successBlock(response)
        } catch (e: NetworkErrorException) {
            errorBlock(e.message, e.errorCode)
        }
    }
}

interface IRequestHandler {
    suspend fun <T> executeRequest(
        request: suspend () -> T,
        successBlock: (T) -> ResponseResult<T>,
        errorBlock: (String?, Int?) -> ResponseResult<T> = { message, _ -> ResponseResult.Error(message) }
    ): ResponseResult<T>
}