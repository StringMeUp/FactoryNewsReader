package com.string.me.up.factorynewsreader.news.network

import com.string.me.up.factorynewsreader.news.data.NewsData
import com.string.me.up.factorynewsreader.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("articles")
    suspend fun getArticles(
        @Query("source") source: String = "bbc-news",
        @Query("sortBy") sortBy: String = "top",
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsData>
}