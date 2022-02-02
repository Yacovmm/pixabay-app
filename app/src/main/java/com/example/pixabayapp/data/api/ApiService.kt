package com.example.pixabayapp.data.api

import com.example.pixabayapp.data.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    suspend fun getData(
        @Query("q")
        q: String = "flowers",
        @Query("image_type")
        imageType: String = "photo",
        @Query("page")
        page: Int = 1,
        @Query("per_page")
        perPage: Int = 20
    ): Response<ApiResponse>
}