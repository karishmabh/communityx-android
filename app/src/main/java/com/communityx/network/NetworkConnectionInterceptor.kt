package com.communityx.network

import android.content.Context
import android.net.ConnectivityManager
import com.communityx.application.MyApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor : Interceptor {

    fun isOnline(): Boolean {
        val connectivityManager = MyApplication.application?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()) {
            throw NoConnectivityException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    inner class NoConnectivityException : IOException() {

        override fun getLocalizedMessage(): String {
            return "Network Connection exception"
        }
    }
}

