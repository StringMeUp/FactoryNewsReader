package com.string.me.up.factorynewsreader.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.string.me.up.factorynewsreader.db.DbArticle
import com.string.me.up.factorynewsreader.news.data.Article
import com.string.me.up.factorynewsreader.news.data.NewsData
import com.string.me.up.factorynewsreader.news.repo.NewsRepository
import com.string.me.up.factorynewsreader.usecase.ProvideArticlesUseCase
import com.string.me.up.factorynewsreader.usecase.ProvideArticlesUseCaseImpl
import com.string.me.up.factorynewsreader.util.Mapper.toArticle
import com.string.me.up.factorynewsreader.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SharedViewModel
@Inject constructor(
    private val articlesUseCase: ProvideArticlesUseCase
) : ViewModel() {

    val newsList = MutableLiveData<List<Article>>()
    val error = MutableLiveData<Int>()
    val isLoading = MutableLiveData<Boolean>()
    var currentPosition = MutableLiveData<Int>()

    fun fetchNewsData() = viewModelScope.launch {
        isLoading.postValue(true)
        articlesUseCase.invoke()
            .onCompletion { isLoading.postValue(false) }
            .collect { news ->
                when (news) {
                    is State.Success<*> -> {
                        @Suppress("UNCHECKED_CAST")
                        newsList.postValue((news.response as List<DbArticle>).map { it.toArticle() })
                    }
                    is State.Failure -> {
                        error.postValue(news.error)
                    }
                }
            }
    }


//        isLoading.postValue(true)
//        newsRepository.provideArticles()
//            .onCompletion { isLoading.postValue(false) }
//            .collect { news ->
//                when (news) {
//                    is State.Success<*> -> {
//                        @Suppress("UNCHECKED_CAST")
//                        newsList.postValue((news.response as List<DbArticle>).map { it.toArticle() })
//                    }
//                    is State.Failure -> {
//                        error.postValue(news.error)
//                    }
//                }
//            }
//}

    fun setCurrentPosition(position: Int) {
        currentPosition.postValue(position)
    }
}