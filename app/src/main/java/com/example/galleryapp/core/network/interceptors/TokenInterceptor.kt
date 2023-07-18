package com.example.galleryapp.core.network.interceptors

import com.example.galleryapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder().apply {
            header("Accept-Version", "v1")
            header("Authorization", "Client-ID ${BuildConfig.API_KEY}")
        }
        return chain.proceed(newRequest.build())
    }
}