package com.example.search

import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkInterface {
    @GET("kakao")
    suspend fun getSearchResult(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Search
}