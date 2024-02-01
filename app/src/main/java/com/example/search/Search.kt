package com.example.search

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("documents")
    val documents: MutableList<Document>
)

data class Document (
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("display_sitename")
    val siteName: String,
    @SerializedName("datetime")
    val datetime: String
)