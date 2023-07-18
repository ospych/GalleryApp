package com.example.galleryapp.logic.repositories

import ImagesPagingSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.galleryapp.core.network.utils.RequestHandler
import com.example.galleryapp.core.network.utils.ResponseResult
import com.example.galleryapp.core.services.ImagesApi
import com.example.galleryapp.core.services.models.PhotoResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imagesApi: ImagesApi,
    private val requestHandler: RequestHandler
) : ImageRepository {
    override suspend fun getImages(): Flow<PagingData<PhotoResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagesPagingSource(imagesApi) }
        ).flow
    }

    override suspend fun getImage(id: String): ResponseResult<PhotoResponse?> {
        val response = requestHandler.executeRequest(
            request = {
                val response = imagesApi.getImage(id)
                response.body()
            },
            successBlock = {
                ResponseResult.Success(it)
            },
            errorBlock = { message, errorCode ->
                ResponseResult.Error(message, errorCode)
            }
        )
        return response
    }

    override suspend fun getImagesState(): PagingSource.LoadResult<Int, PhotoResponse> {
        val pagingSource = ImagesPagingSource(imagesApi)
        val loadParams = PagingSource.LoadParams.Refresh(0, 10, false)
        return pagingSource.load(loadParams)
    }
}