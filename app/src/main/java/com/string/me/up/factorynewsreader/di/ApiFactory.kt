package com.string.me.up.factorynewsreader.di

interface ApiFactory {
    fun <T> buildApi(type: Class<T>): T
}
