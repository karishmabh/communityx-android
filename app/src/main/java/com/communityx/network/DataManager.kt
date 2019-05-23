package com.communityx.network

import android.app.Activity
import com.communityx.models.oauth.OAuthRequest
import com.communityx.models.oauth.OAuthResponse
import com.communityx.utils.AppConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    fun getBasicAuth(activity: Activity, dataManagerListener: DataManagerListener) {
        val call = getDataManager().getBasicAuth(OAuthRequest())
        call.enqueue(object : Callback<OAuthResponse> {
            override fun onResponse(call: Call<OAuthResponse>, response: Response<OAuthResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { dataManagerListener.onError(it) }
                    return
                }

                if (response.body()?.status != null && response.body()?.status!!.equals("success"))
                    dataManagerListener.onSuccess(response.body()!!.data)
                else
                    dataManagerListener.onError(response.body()!!.error)
            }

            override fun onFailure(call: Call<OAuthResponse>, t: Throwable) {
                dataManagerListener.onError(t)
            }
        })
    }

}