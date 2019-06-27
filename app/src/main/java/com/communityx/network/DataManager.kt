package com.communityx.network

import android.app.Activity
import com.communityx.models.connect_allies.ConnectAlliesResponse
import com.communityx.models.connect_allies.ProfileData
import com.communityx.models.login.LoginRequest
import com.communityx.models.login.LoginResponse
import com.communityx.models.logout.LogoutResponse
import com.communityx.models.myallies.all_allies.AllAlliesResponse
import com.communityx.models.myallies.invitation.AlliesInvitationResponse
import com.communityx.models.signup.EmailPhoneVerificationRequest
import com.communityx.models.signup.VerificationResponse
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

    private fun getMockDataManager(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(AppConfiguration.MOCK_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun getService(): IApiInterface {
        return getDataManager()!!.create(IApiInterface::class.java)
    }


    fun getMockService(): IApiInterface {
        return getMockDataManager()!!.create(IApiInterface::class.java)
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

    fun doVerifyEmailPhone(activity: Activity, emailPhoneVerificationRequest: EmailPhoneVerificationRequest, listener: ResponseListener<List<String>>) {
        val call = DataManager.getService().verifyUser(AuthRepo.getAccessToken(activity), AuthRepo.getSessionId(activity), emailPhoneVerificationRequest)
        call.enqueue(object  : Callback<VerificationResponse> {
            override fun onFailure(call: Call<VerificationResponse>, t: Throwable) {
                listener.onError(t)
            }

            override fun onResponse(call: Call<VerificationResponse>, response: Response<VerificationResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { listener.onError(it) }
                    return
                }

                if (response.body()?.status != null && response.body()?.status == AppConstant.STATUS_SUCCESS)
                    listener.onSuccess(response.body()!!.data)
                else
                    listener.onError(response.body()!!.error)
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

    fun getAllAllies(activity: Activity, listener: ResponseListener<AllAlliesResponse>) {
        val call = DataManager.getMockService().getAllAllies(AuthRepo.getAccessToken(activity), AuthRepo.getSessionId(activity))
        call.enqueue(object : Callback<AllAlliesResponse> {
            override fun onResponse(call: Call<AllAlliesResponse>, response: Response<AllAlliesResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { listener.onError(it) }
                    return
                }

                if (response.body()?.status != null && response.body()?.status == AppConstant.STATUS_SUCCESS)
                    listener.onSuccess(response.body()!!)
                else
                    listener.onError(response.body()!!.error)
            }

            override fun onFailure(call: Call<AllAlliesResponse>, t: Throwable) {
                listener.onError(t)
            }
        })
    }

    fun getAlliesInvitation(activity: Activity, listener: ResponseListener<AlliesInvitationResponse>) {
        val call = DataManager.getMockService().getAlliesInvitations(AuthRepo.getAccessToken(activity), AuthRepo.getSessionId(activity))
        call.enqueue(object : Callback<AlliesInvitationResponse> {
            override fun onResponse(call: Call<AlliesInvitationResponse>, response: Response<AlliesInvitationResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { listener.onError(it) }
                    return
                }

                if (response.body()?.status != null && response.body()?.status == AppConstant.STATUS_SUCCESS)
                    listener.onSuccess(response.body()!!)
                else
                    listener.onError(response.body()!!.error)
            }

            override fun onFailure(call: Call<AlliesInvitationResponse>, t: Throwable) {
                listener.onError(t)
            }
        })
    }

    fun getAlliesSuggestions(activity: Activity, listener: ResponseListener<AlliesInvitationResponse>) {
        val call = DataManager.getMockService().getAlliesSuggestions(AuthRepo.getAccessToken(activity), AuthRepo.getSessionId(activity))
        call.enqueue(object : Callback<AlliesInvitationResponse> {
            override fun onResponse(call: Call<AlliesInvitationResponse>, response: Response<AlliesInvitationResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { listener.onError(it) }
                    return
                }

                if (response.body()?.status != null && response.body()?.status == AppConstant.STATUS_SUCCESS)
                    listener.onSuccess(response.body()!!)
                else
                    listener.onError(response.body()!!.error)
            }

            override fun onFailure(call: Call<AlliesInvitationResponse>, t: Throwable) {
                listener.onError(t)
            }
        })
    }
}