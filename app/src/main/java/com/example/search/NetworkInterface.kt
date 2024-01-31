package com.example.search

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
const val REST_API_KEY = "c01fc2286a8db4743a7ad1050ab6090f"
interface NetworkInterface {
    @GET("v2/search/image")
    fun getSearchResult(
        @Header("Authorization") apiKey: String = "KakaoAK $REST_API_KEY",
        @Query("query") query: String,
        @Query("size") size: Int = 80
    ): Call<Search>
}