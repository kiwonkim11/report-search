package com.example.search

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val REST_API_KEY = "c01fc2286a8db4743a7ad1050ab6090f"
interface NetworkInterface {
    @Headers("Authorization: KakaoAK ${REST_API_KEY}")
    @GET("kakao")
    suspend fun getSearchResult(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 80
    ): Search
}