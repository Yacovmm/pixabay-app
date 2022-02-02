package com.example.pixabayapp.data.interceptors

import com.example.pixabayapp.utils.Constants.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        val urlWithToken = request.url.newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(urlWithToken)
                .build()
        )
    }
}