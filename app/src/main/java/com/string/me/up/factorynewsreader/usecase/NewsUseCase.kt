package com.string.me.up.factorynewsreader.usecase

import com.string.me.up.factorynewsreader.db.DbArticle
import com.string.me.up.factorynewsreader.news.repo.NewsRepository
import com.string.me.up.factorynewsreader.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProvideArticlesUseCase {
    operator fun invoke(): Flow<Resource<List<DbArticle>>>
}


class ProvideArticlesUseCaseImpl
@Inject constructor(
    private val repository: NewsRepository) : ProvideArticlesUseCase {
    override fun invoke(): Flow<Resource<List<DbArticle>>> {
        return repository.provideArticles()
    }
}