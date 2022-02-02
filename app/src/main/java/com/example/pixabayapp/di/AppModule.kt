package com.example.pixabayapp.di

import com.example.pixabayapp.BuildConfig
import com.example.pixabayapp.data.PixabayRepository
import com.example.pixabayapp.data.api.ApiService
import com.example.pixabayapp.data.interceptors.TokenInterceptor
import com.example.pixabayapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor())
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor())
                .build()
        }
    }


    @Provides
    @Singleton
    fun provideRetrofitInstance(
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(service: ApiService) = PixabayRepository(service)

}