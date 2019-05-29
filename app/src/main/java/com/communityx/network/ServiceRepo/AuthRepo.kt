package com.communityx.network.ServiceRepo

import android.app.Activity
import android.content.Context
import com.communityx.models.oauth.OAuthRequest
import com.communityx.models.oauth.OAuthResponse
import com.communityx.models.oauth.OauthData
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.ACCESS_TOKEN_KEY
import com.communityx.utils.AppConstant.STATUS_SUCCESS
import com.communityx.utils.AppPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthRepo : BaseRepo {

    fun getBasicAuth(activity: Activity, listener: ResponseListener<List<OauthData>>) {
        val call = DataManager.getService(activity).getBasicAuth(OAuthRequest())
        call.enqueue(object : Callback<OAuthResponse> {
            override fun onResponse(call: Call<OAuthResponse>, response: Response<OAuthResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { listener.onError(it) }
                    return
                }

                if (response.body()?.status != null && response.body()?.status!! == STATUS_SUCCESS)
                    listener.onSuccess(response.body()!!.data)
                else
                    listener.onError(response.body()!!.error)
            }

            override fun onFailure(call: Call<OAuthResponse>, t: Throwable) {
                listener.onError(t)
            }
        })
    }

    fun saveAccessToken(context: Context, accessToken: String) {
        AppPreference.getInstance(context).setString(ACCESS_TOKEN_KEY, accessToken)
    }

    fun getAccessToken(context: Context) :String {
        return AppPreference.getInstance(context).getString(ACCESS_TOKEN_KEY)
    }
}