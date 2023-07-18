package com.example.galleryapp.core.network.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            val response = chain.proceed(request)
            if (response.isSuccessful) {
                return chain.proceed(request)
            } else {
                val responseMessage = response.body?.string()
                throw errorThrow(
                    code = response.code,
                    message = responseMessage
                )
            }
        } catch (e: SocketTimeoutException) {
            throw errorThrow(null, "No internet")
        } catch (e: UnknownHostException) {
            throw errorThrow(null, "No internet")
        } catch (e: IOException) {
            val response = chain.proceed(request)
            val responseMessage = response.body?.string()
            throw errorThrow(
                code = response.code,
                message = responseMessage
            )
        }
    }

    private fun errorThrow(code: Int?, message: String?): NetworkErrorException {
        return NetworkErrorException(
            message = "$message",
            errorCode = code
        )
    }
}
class NetworkErrorException(message: String, val errorCode: Int?) : IOException(message)