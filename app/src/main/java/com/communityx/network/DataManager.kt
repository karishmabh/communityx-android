package com.communityx.network

import android.app.Activity
import com.communityx.models.connect_allies.ConnectAlliesResponse
import com.communityx.models.connect_allies.Data
import com.communityx.models.connect_allies.ProfileData
import com.communityx.models.login.LoginRequest
import com.communityx.models.login.LoginResponse
import com.communityx.models.logout.LogoutResponse
import com.communityx.network.serviceRepo.AuthRepo
import com.communityx.utils.AppConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataManager : AppConstant {

    private var retrofit: Retrofit? = null
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(NetworkConnectionInterceptor())
        .addInterceptor(interceptor)
        .build()

    private fun getDataManager(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(AppConfiguration.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun getService(): IApiInterface {
        return getDataManager()!!.create(IApiInterface::class.java)
    }

    fun doLogin(activity: Activity, loginRequest: LoginRequest, listener: ResponseListener<LoginResponse>) {
        val call = DataManager.getService().doLogin(AuthRepo.getAccessToken(activity), loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { listener.onError(it) }
                    return
                }

                if (response.body()?.status != null && response.body()?.status == AppConstant.STATUS_SUCCESS)
                    listener.onSuccess(response.body()!!)
                else
                    listener.onError(response.body()!!.error)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                listener.onError(t)
            }
        })
    }

    fun doLogOut(activity: Activity, listener: ResponseListener<LogoutResponse>) {
        val call = DataManager.getService().logout(AuthRepo.getAccessToken(activity), AuthRepo.getSessionId(activity))
        call.enqueue(object : Callback<LogoutResponse> {
            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { listener.onError(it) }
                    return
                }

                if (response.body()?.status != null && response.body()?.status == AppConstant.STATUS_SUCCESS)
                    listener.onSuccess(response.body()!!)
                else
                    listener.onError(response.body()!!.error)
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                listener.onError(t)
            }
        })
    }

    fun getConnectingAllies(activity: Activity, listener: ResponseListener<List<ProfileData>>) {
        val call = DataManager.getService().getConnectingAllies(AuthRepo.getAccessToken(activity), AuthRepo.getSessionId(activity))
        call.enqueue(object : Callback<ConnectAlliesResponse> {
            override fun onResponse(call: Call<ConnectAlliesResponse>, response: Response<ConnectAlliesResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { listener.onError(it) }
                    return
                }

                if (response.body()?.status != null && response.body()?.status == AppConstant.STATUS_SUCCESS)
                    listener.onSuccess(response.body()!!.data.get(0).data)
                else
                    listener.onError(response.body()!!.error)
            }

            override fun onFailure(call: Call<ConnectAlliesResponse>, t: Throwable) {
                listener.onError(t)
            }
        })
    }
}