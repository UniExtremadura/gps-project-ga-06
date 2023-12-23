package com.aseegpsproject.openbook

import com.aseegpsproject.openbook.api.OpenLibraryAPI
import com.aseegpsproject.openbook.data.dummyNetworkAuthorDetailResponse
import com.aseegpsproject.openbook.data.dummyNetworkSearchAuthorsResponse
import com.aseegpsproject.openbook.data.dummyNetworkSearchWorksResponse
import com.aseegpsproject.openbook.data.dummyNetworkTrendingResponse
import com.aseegpsproject.openbook.data.dummyNetworkWorkDetailResponse
import com.aseegpsproject.openbook.data.dummyNetworkWorkRatingsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class OpenLibraryAPITest {
    @Mock
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
    }

    @Test
    fun getTrendingWorks() {
        val mockResponse = MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody(dummyNetworkTrendingResponse.toString())

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("trending/daily.json")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(OpenLibraryAPI::class.java).getDailyTrendingBooks("daily")
            assertEquals(service, dummyNetworkTrendingResponse)
        }
    }

    @Test
    fun getWorkDetail() {
        val mockResponse = MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody(dummyNetworkWorkDetailResponse.toString())

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("works/OL7353617W.json")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(OpenLibraryAPI::class.java).getWorkInfo("OL7353617W")
            assertEquals(service, dummyNetworkWorkDetailResponse)
        }
    }

    @Test
    fun getAuthorDetail() {
        val mockResponse = MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody(dummyNetworkAuthorDetailResponse.toString())

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("authors/OL1A.json")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(OpenLibraryAPI::class.java).getAuthorInfo("OL1A")
            assertEquals(service, dummyNetworkAuthorDetailResponse)
        }
    }

    @Test
    fun getSearchWorks() {
        val mockResponse = MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody(dummyNetworkSearchWorksResponse.toString())

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("search.json")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(OpenLibraryAPI::class.java).getSearchBooksByTitle("Harry Potter", 1)
            assertEquals(service, dummyNetworkSearchWorksResponse)
        }
    }

    @Test
    fun getSearchAuthors() {
        val mockResponse = MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody(dummyNetworkSearchAuthorsResponse.toString())

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("search/authors.json")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(OpenLibraryAPI::class.java).getSearchAuthorsByName("J.K. Rowling", 1)
            assertEquals(service, dummyNetworkSearchAuthorsResponse)
        }
    }

    @Test
    fun getWorkRatings() {
        val mockResponse = MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody(dummyNetworkWorkRatingsResponse.toString())

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("works/OL7353617W/ratings.json")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(OpenLibraryAPI::class.java).getWorkRatings("OL7353617W")
            assertEquals(service, dummyNetworkWorkRatingsResponse)
        }
    }
}