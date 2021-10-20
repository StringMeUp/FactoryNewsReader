package com.string.me.up.factorynewsreader.util

import com.string.me.up.factorynewsreader.db.DbArticle

sealed class State {

    class Success(val response: List<DbArticle>) : State()
    class Failure(val error: Int) : State()
}
