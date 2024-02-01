package com.example.search

data class SearchItem (
    var title: String,
    var dateTime: String,
    var url: String,
    var isLike: Boolean = false
)