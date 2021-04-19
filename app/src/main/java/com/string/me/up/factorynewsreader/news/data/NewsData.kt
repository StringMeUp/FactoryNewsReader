package com.string.me.up.factorynewsreader.news.data

import com.google.gson.annotations.SerializedName

data class NewsData(
    val status: String,
    val source: String,
    @SerializedName("sortBy") //not necessary just to showcase @SerializedName
    val sortBy: String,
    val articles: List<Article>
)

data class Article(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String?
)