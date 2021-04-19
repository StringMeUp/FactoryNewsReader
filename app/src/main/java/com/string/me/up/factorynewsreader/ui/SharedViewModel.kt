package com.string.me.up.factorynewsreader.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.string.me.up.factorynewsreader.db.DbArticle
import com.string.me.up.factorynewsreader.news.data.Article
import com.string.me.up.factorynewsreader.news.repo.NewsRepository
import com.string.me.up.factorynewsreader.util.Helper
import com.string.me.up.factorynewsreader.util.Helper.toArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel
@Inject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {
    val newsList = MutableLiveData<List<Article>>()
    val error = MutableLiveData<Int>()
    val isLoading = MutableLiveData<Boolean>()

    fun fetchNewsData() = viewModelScope.launch {
        isLoading.postValue(true)
        newsRepository.provideArticles()
            .onCompletion { isLoading.postValue(false) }
            .collect { news ->
                when (news) {
                    is Helper.State.Success<*> -> {
                        @Suppress("UNCHECKED_CAST")
                        newsList.postValue((news.response as List<DbArticle>).map { it.toArticle() })
                    }
                    is Helper.State.Failure -> {
                        error.postValue(news.error)
                    }
                }
            }
    }
}