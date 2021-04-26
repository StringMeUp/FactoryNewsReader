package com.string.me.up.factorynewsreader.news.repo

import com.string.me.up.factorynewsreader.R
import com.string.me.up.factorynewsreader.db.ArticlesDao
import com.string.me.up.factorynewsreader.news.network.NewsApi
import com.string.me.up.factorynewsreader.util.Helper
import com.string.me.up.factorynewsreader.util.Mapper.transform
import com.string.me.up.factorynewsreader.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ApiRepository {
    suspend fun provideArticles(): Flow<State>
    suspend fun fetchNews(): State
}

class NewsRepository
@Inject constructor(
    private val newsApi: NewsApi,
    private val articlesDao: ArticlesDao
) : ApiRepository {
    override suspend fun provideArticles(): Flow<State> = flow {
        val empty = articlesDao.getArticles().isEmpty()
        if (empty) emit(fetchNews())
        articlesDao.getTimeStamp()?.let {
            val expired = System.currentTimeMillis() > it + Helper.getRefreshTime()
            if (expired) emit(fetchNews())
        }
        emit(State.Success(articlesDao.getArticles()))
    }

    override suspend fun fetchNews(): State {
        val errorResult = State.Failure(R.string.error_message_label)
        return try {
            val response = newsApi.getArticles()
            if (response.isSuccessful) {
                articlesDao.delete()
                articlesDao.insertWithTimestamp(response.transform())
                State.Success(response.transform())
            } else {
                errorResult
            }
        } catch (e: Exception) {
            errorResult
        }
    }
}