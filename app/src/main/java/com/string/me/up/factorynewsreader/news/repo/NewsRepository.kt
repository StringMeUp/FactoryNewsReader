package com.string.me.up.factorynewsreader.news.repo

import com.string.me.up.factorynewsreader.R
import com.string.me.up.factorynewsreader.db.ArticlesDao
import com.string.me.up.factorynewsreader.news.network.NewsApi
import com.string.me.up.factorynewsreader.util.Helper
import com.string.me.up.factorynewsreader.util.Helper.toDbArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ApiRepository {
    suspend fun provideArticles(): Flow<Helper.State>
    suspend fun fetchNews(): Helper.State
}

class NewsRepository
@Inject constructor(
    private val newsApi: NewsApi,
    private val articlesDao: ArticlesDao
) : ApiRepository {
    override suspend fun provideArticles(): Flow<Helper.State> = flow {
        val empty = articlesDao.getArticles().isEmpty()
        if (empty) emit(fetchNews())
        articlesDao.getTimeStamp()?.let {
            val expired = System.currentTimeMillis() > it + Helper.refreshTime()
            if (expired) emit(fetchNews())
        }
        emit(Helper.State.Success(articlesDao.getArticles()))
    }

    override suspend fun fetchNews(): Helper.State {
        return try {
            val response = newsApi.getArticles()
            if (response.isSuccessful) {
                articlesDao.delete()
                articlesDao.insertWithTimestamp(response.body()!!.articles.map { it.toDbArticle() })
                Helper.State.Success(response.body()!!.articles.map { it.toDbArticle() })
            } else {
                Helper.State.Failure(R.string.error_message_label)
            }
        } catch (e: Exception) {
            Helper.State.Failure(R.string.error_message_label)
        }
    }
}
