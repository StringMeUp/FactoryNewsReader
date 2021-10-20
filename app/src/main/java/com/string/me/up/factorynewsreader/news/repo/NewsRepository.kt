package com.string.me.up.factorynewsreader.news.repo

import com.string.me.up.factorynewsreader.R
import com.string.me.up.factorynewsreader.db.ArticlesDao
import com.string.me.up.factorynewsreader.db.DbArticle
import com.string.me.up.factorynewsreader.news.network.NewsApi
import com.string.me.up.factorynewsreader.util.Helper
import com.string.me.up.factorynewsreader.util.Mapper.transform
import com.string.me.up.factorynewsreader.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ApiRepository {
    fun provideArticles(): Flow<Resource<List<DbArticle>>>
    suspend fun fetchOrRefreshNews(): Resource<List<DbArticle>>
}

class NewsRepository
@Inject constructor(
    private val newsApi: NewsApi,
    private val articlesDao: ArticlesDao
) : ApiRepository {
    override fun provideArticles(): Flow<Resource<List<DbArticle>>> = flow {
        val empty = articlesDao.getArticles().isEmpty()
        /**Emit Loading state*/
        emit(Resource.Loading())
        /**If empty fetch data*/
        if (empty) emit(fetchOrRefreshNews())
        articlesDao.getTimeStamp()?.let {
            emit(Resource.Loading())
            val expired = System.currentTimeMillis() > it + Helper.getRefreshTime()
            /**If expired fetch new data*/
            if (expired) emit(fetchOrRefreshNews())
        }
        emit(Resource.Success(articlesDao.getArticles()))
    }

    override suspend fun fetchOrRefreshNews(): Resource<List<DbArticle>> {
        return try {
            val response = newsApi.getArticles()
            Resource.Loading(null)
            if (response.isSuccessful) {
                articlesDao.delete()
                articlesDao.insertWithTimestamp(response.transform())
                Resource.Success(response.transform())
            } else {
              Resource.Error(R.string.error_message_label)
            }
        } catch (e: Exception) {
            Resource.Error(R.string.error_message_label)
        }
    }
}