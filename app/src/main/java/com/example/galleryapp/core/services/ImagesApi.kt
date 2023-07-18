package com.example.galleryapp.core.services

import com.example.galleryapp.core.services.models.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImagesApi {
    @GET("photos")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<PhotoResponse>>

    @GET("photos/{id}")
    suspend fun getImage(
        @Path("id") id: String
    ): Response<PhotoResponse>
}