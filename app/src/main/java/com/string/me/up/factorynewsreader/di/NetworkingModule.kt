package com.string.me.up.factorynewsreader.di

import com.string.me.up.factorynewsreader.BuildConfig
import com.string.me.up.factorynewsreader.news.network.NewsApi
import com.string.me.up.factorynewsreader.news.repo.NewsRepository
import com.string.me.up.factorynewsreader.usecase.ProvideArticlesUseCase
import com.string.me.up.factorynewsreader.usecase.ProvideArticlesUseCaseImpl
import com.string.me.up.factorynewsreader.util.Constants
import com.string.me.up.factorynewsreader.util.Helper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Singleton
    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideOkClient(): OkHttpClient = createUnsafeOkHttpClient()

    private fun createUnsafeOkHttpClient(): OkHttpClient {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .connectTimeout(Constants.TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(Constants.TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(Constants.TIME_OUT, TimeUnit.MILLISECONDS)
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitApiFactory(): NewsApi = RetrofitApiFactory(
        provideBaseUrl(),
        provideOkClient()
    ).buildApi(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideNewsUseCase(repository: NewsRepository): ProvideArticlesUseCase{
        return ProvideArticlesUseCaseImpl(repository)
    }
}