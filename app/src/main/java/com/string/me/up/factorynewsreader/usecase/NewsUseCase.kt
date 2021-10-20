package com.string.me.up.factorynewsreader.usecase

import com.string.me.up.factorynewsreader.news.repo.NewsRepository
import com.string.me.up.factorynewsreader.util.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProvideArticlesUseCase {
    operator fun invoke(): Flow<State>
}


class ProvideArticlesUseCaseImpl
@Inject constructor(
    private val repository: NewsRepository) : ProvideArticlesUseCase {
    override fun invoke(): Flow<State> {
        return repository.provideArticles()
    }
}