package com.communityx.network

import com.communityx.utils.AppConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DataManager : AppConstant {

    private var retrofit: Retrofit? = null
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(interceptor)
        .build()

    fun getInstance(): DataManager {
        return ApiClientSingleton.INSTANCE
    }

    private fun getDataManager(): IApiInterface {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(AppConfiguration.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!.create(IApiInterface::class.java)
    }

    interface DataManagerListener {
        fun onSuccess(response: Any)

        fun onError(error: Any)
    }

    object ApiClientSingleton {
        val INSTANCE = DataManager()
    }
}