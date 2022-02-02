package com.example.pixabayapp.data

import com.example.pixabayapp.data.api.ApiService
import com.example.pixabayapp.data.models.ApiResponse
import com.example.pixabayapp.utils.SafeApiCall
import retrofit2.Response
import javax.inject.Inject

class PixabayRepository @Inject constructor(
    private val service: ApiService
) {

    suspend fun getImages(
        q: String = "flowers",
        page: Int = 1,
        perPage: Int = 20
    ): Response<ApiResponse>? {
        return SafeApiCall.safeNetworkRequest {
            service.getData(
                page = page,
                perPage = perPage
            )
        }

    }

}