package com.string.me.up.factorynewsreader.di

import com.string.me.up.factorynewsreader.BuildConfig
import com.string.me.up.factorynewsreader.news.network.NewsApi
import com.string.me.up.factorynewsreader.util.Constants
import com.string.me.up.factorynewsreader.util.Helper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Singleton
    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideOkClient(): OkHttpClient = createOkHttpClient()

    private fun createOkHttpClient(
    ): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        return builder
            .connectTimeout(Constants.TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(Constants.TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(Constants.TIME_OUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitApiFactory(): NewsApi = RetrofitApiFactory(
        provideBaseUrl(),
        provideOkClient()
    ).buildApi(NewsApi::class.java)
}