package com.example.search

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val REST_API_KEY = "c01fc2286a8db4743a7ad1050ab6090f"
interface NetworkInterface {
    @GET("kakao")
    fun getSearchResult(
        @Header("Authorization: KakaoAK ${REST_API_KEY}")
        @Query("query") query: String,
        @Query("size") size: Int = 80
    ): Call<Search>
}