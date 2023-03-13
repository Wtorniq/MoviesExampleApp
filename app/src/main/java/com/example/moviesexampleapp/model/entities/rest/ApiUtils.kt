package com.example.moviesexampleapp.model.entities.rest

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {
    private val baseUrlMainPart = "https://kinopoiskapiunofficial.tech/api/"
    private val baseUrlVersion = "v2.2/"
    val baseUrl = "$baseUrlMainPart$baseUrlVersion"

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("X-API-KEY", "09b396a2-9c2e-4baa-aec7-809851f46a7d")
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }

        return httpClient.build()
    }
}