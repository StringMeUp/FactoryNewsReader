package com.string.me.up.factorynewsreader.util

import com.string.me.up.factorynewsreader.db.DbArticle
import com.string.me.up.factorynewsreader.news.data.Article
import com.string.me.up.factorynewsreader.news.data.NewsData
import retrofit2.Response

object Mapper {

    private fun Article.toDbArticle() = DbArticle(
        this.author,
        this.title,
        this.description,
        this.url,
        this.urlToImage,
        this.publishedAt
    )

    fun DbArticle.toArticle() = Article(
        this.author,
        this.title,
        this.description,
        this.url,
        this.urlToImage,
        this.publishedAt
    )

    fun Response<NewsData>.transform(): List<DbArticle>{
        return this.body()!!.articles.map{it.toDbArticle()}
    }
}