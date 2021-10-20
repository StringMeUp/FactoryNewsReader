package com.string.me.up.factorynewsreader.util

import com.string.me.up.factorynewsreader.db.DbArticle

sealed class State {

    class Success(val response: List<DbArticle>) : State()
    class Failure(val error: Int) : State()
}


sealed class Resource<T>(
    val data: T? = null,
    val message: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: Int, data: T? = null) : Resource<T>(data, message)
}