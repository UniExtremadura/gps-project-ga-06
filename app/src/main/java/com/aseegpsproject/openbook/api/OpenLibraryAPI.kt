package com.aseegpsproject.openbook.api

import com.aseegpsproject.openbook.data.apimodel.APIAuthor
import com.aseegpsproject.openbook.data.apimodel.APIAuthorDeserializer
import com.aseegpsproject.openbook.data.apimodel.APIWork
import com.aseegpsproject.openbook.data.apimodel.APIWorkDeserializer
import com.aseegpsproject.openbook.data.apimodel.Rating
import com.aseegpsproject.openbook.data.apimodel.SearchQuery
import com.aseegpsproject.openbook.data.apimodel.TrendingQuery
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://openlibrary.org/"

private val service: OpenLibraryAPI by lazy {
    val okHttpClient = OkHttpClient.Builder()
        //.addInterceptor(SkipNetworkInterceptor())
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().registerTypeAdapter(
                    APIAuthor::class.java,
                    APIAuthorDeserializer()
                ).registerTypeAdapter(APIWork::class.java, APIWorkDeserializer()).create()
            )
        )
        .build()

    retrofit.create(OpenLibraryAPI::class.java)
}

fun getNetworkService() = service

interface OpenLibraryAPI {
    @GET("trending/{freq}.json")
    suspend fun getDailyTrendingBooks(
        @Path("freq", encoded = true) freq: String,
        @Query("limit") limit: Int = 25
    ): TrendingQuery

    @GET("search.json")
    suspend fun getSearchBooksByTitle(
        @Query("title") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 25
    ): SearchQuery

    @GET("authors/{key}.json")
    suspend fun getAuthorInfo(
        @Path("key", encoded = true) key: String
    ): APIAuthor

    @GET("search/authors.json")
    suspend fun getSearchAuthorsByName(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): SearchQuery

    @GET("{key}.json")
    suspend fun getWorkInfo(
        @Path("key", encoded = true) key: String
    ): APIWork

    @GET("{key}/ratings.json")
    suspend fun getWorkRatings(
        @Path("key", encoded = true) key: String
    ): Rating
}

class APIError(message: String, cause: Throwable? = null) : Throwable(message, cause)