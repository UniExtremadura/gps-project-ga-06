package com.aseegpsproject.openbook.api

import com.aseegpsproject.openbook.data.apimodel.TrendingQuery
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://openlibrary.org/"

private val service: OpenLibraryAPI by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(OpenLibraryAPI::class.java)
}

fun getNetworkService() = service

interface OpenLibraryAPI {
    @GET("trending/daily.json")
    suspend fun getDailyTrendingBooks() : TrendingQuery
}

class APIError(message: String, cause: Throwable? = null) : Throwable(message, cause)