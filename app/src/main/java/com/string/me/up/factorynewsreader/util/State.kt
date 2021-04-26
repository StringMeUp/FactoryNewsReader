package com.string.me.up.factorynewsreader.util

sealed class State {

    class Success<T>(val response: T) : State()
    class Failure(val error: Int) : State()
}
