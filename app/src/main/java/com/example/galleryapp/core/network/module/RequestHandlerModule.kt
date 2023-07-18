package com.example.galleryapp.core.network.module

import com.example.galleryapp.core.network.utils.IRequestHandler
import com.example.galleryapp.core.network.utils.RequestHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RequestHandlerModule {
    @Provides
    fun bindRequestHandler(requestHandler: RequestHandler): IRequestHandler {
        return requestHandler
    }
}