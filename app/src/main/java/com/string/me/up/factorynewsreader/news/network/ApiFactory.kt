package com.string.me.up.factorynewsreader.news.network

interface ApiFactory {
    fun <T> buildApi(type: Class<T>): T
}
