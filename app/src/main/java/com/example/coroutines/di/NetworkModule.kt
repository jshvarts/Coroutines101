package com.example.coroutines.di

import com.example.coroutines.repository.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val GITHUB_BASE_URL = "https://api.github.com/"

@Module
class NetworkModule {
    @Provides
    fun provideRetrofit(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .baseUrl(GITHUB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
            .create(ApiService::class.java)
    }
}