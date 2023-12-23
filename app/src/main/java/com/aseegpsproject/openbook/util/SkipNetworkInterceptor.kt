package com.aseegpsproject.openbook.util

import com.aseegpsproject.openbook.data.dummyNetworkAuthorDetailResponse
import com.aseegpsproject.openbook.data.dummyNetworkSearchAuthorsResponse
import com.aseegpsproject.openbook.data.dummyNetworkSearchWorksResponse
import com.aseegpsproject.openbook.data.dummyNetworkTrendingResponse
import com.aseegpsproject.openbook.data.dummyNetworkWorkDetailResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * This class will return fake [Response] objects to Retrofit, without actually using the network.
 */
class SkipNetworkInterceptor : Interceptor {
    //    private var lastResult: String = ""
    val gson = Gson()

    private var attempts = 0

    /**
     * Return true iff this request should error.
     */
    private fun wantRandomError() = attempts++ % 5 == 0

    /**
     * Stop the request from actually going out to the network.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (chain.request().url.toString().contains("trending"))
            makeTrendingOkResult(chain.request())
        else if (chain.request().url.toString().contains("works"))
            makeDetailOkResult(chain.request())
        else if (chain.request().url.toString().contains("search/authors.json"))
            makeSearchAuthorsResponse(chain.request())
        else if (chain.request().url.toString().contains("search.json"))
            makeSearchWorksResponse(chain.request())
        else if (chain.request().url.toString().contains("authors"))
            makeAuthorDetailOkResult(chain.request())
        else
            makeErrorResult(chain.request())
    }

    /**
     * Pretend to "block" interacting with the network.
     *
     * Really: sleep for 500ms.
     */
    private fun pretendToBlockForNetworkRequest() = Thread.sleep(500)

    /**
     * Generate an error result.
     *
     * ```
     * HTTP/1.1 500 Bad server day
     * Content-type: application/json
     *
     * {"cause": "not sure"}
     * ```
     */
    private fun makeErrorResult(request: Request): Response {
        return Response.Builder()
            .code(500)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("Bad server day")
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                    gson.toJson(mapOf("cause" to "not sure"))
                )
            )
            .build()
    }

    private fun makeSearchWorksResponse(request: Request): Response {
        return Response.Builder()
            .code(200)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                    gson.toJson(dummyNetworkSearchWorksResponse)
                )
            )
            .build()
    }


    /**
     * Generate a success response.
     *
     * ```
     * HTTP/1.1 200 OK
     * Content-type: application/json
     *
     * "$random_string"
     * ```
     */
    private fun makeSearchAuthorsResponse(request: Request): Response {
        return Response.Builder()
            .code(200)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                    gson.toJson(dummyNetworkSearchAuthorsResponse)
                )
            )
            .build()
    }

    /**
     * Generate a success response.
     *
     * ```
     * HTTP/1.1 200 OK
     * Content-type: application/json
     *
     * "$random_string"
     * ```
     */
    private fun makeTrendingOkResult(request: Request): Response {
        return Response.Builder()
            .code(200)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                    gson.toJson(dummyNetworkTrendingResponse)
                )
            )
            .build()
    }

    private fun makeDetailOkResult(request: Request): Response {
        return Response.Builder()
            .code(200)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                    gson.toJson(dummyNetworkWorkDetailResponse)
                )
            )
            .build()
    }

    private fun makeAuthorDetailOkResult(request: Request): Response {
        return Response.Builder()
            .code(200)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                    gson.toJson(dummyNetworkAuthorDetailResponse)
                )
            )
            .build()
    }
}