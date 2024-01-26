package com.example.search

import com.google.gson.annotations.SerializedName
import java.time.DateTimeException

data class Search(
    @SerializedName("meta")
    val searchMeta: SearchMeta,
    @SerializedName("documents")
    val searchDocuments: SearchDocuments
)

data class SearchMeta (
    val total_count: Int,
    val papageable_count: Int,
    val is_end: Boolean
)

data class SearchDocuments (
    val collection: String,
    val thumbnail_url: String,
    val image_url: String,
    val width: Int,
    val height: Int,
    val display_sitename: String,
    val doc_url: String,
    val datetime: Datetime
)