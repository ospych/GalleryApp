package com.example.galleryapp.logic.repositories

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.galleryapp.core.network.utils.ResponseResult
import com.example.galleryapp.core.services.models.PhotoResponse
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getImages(): Flow<PagingData<PhotoResponse>>
    suspend fun getImagesState(): PagingSource.LoadResult<Int, PhotoResponse>
    suspend fun getImage(id: String): ResponseResult<PhotoResponse?>
}