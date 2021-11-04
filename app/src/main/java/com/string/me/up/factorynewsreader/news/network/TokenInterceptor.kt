package com.string.me.up.factorynewsreader.news.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)


//        if (response.code == 200) {
//            Handler(Looper.getMainLooper()).postDelayed(
//                { refresh(request, chain) }, 2000
//            )
//        }

        return response
    }
}

private fun refresh(request: Request, chain: Interceptor.Chain) = runBlocking {
    val newToken: String? = null
    //fetch new access token by sending refresh token
    //save the new access token
    request.newBuilder().addHeader("Bearer", "eyxhsgyeuyf.hjdhdhdhd.dhdhdhdhdhdh").build()
    Log.d("NEWREQUEST", "refresh: $request ")
    chain.proceed(request)
}