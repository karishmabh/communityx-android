package com.communityx.network.ServiceRepo

import android.content.Context
import com.communityx.models.signup.MajorMinorResponse
import com.communityx.models.signup.MinorsData
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.AppConstant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SignUpRepo : BaseRepo {

    fun getMajorMinorData(context: Context, responseListener: ResponseListener<List<MinorsData>>) {
        DataManager.getService().getMajorMinor(AuthRepo.getAccessToken(context)).enqueue(object : Callback<MajorMinorResponse> {
            override fun onFailure(call: Call<MajorMinorResponse>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<MajorMinorResponse>, response: Response<MajorMinorResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { responseListener.onError(it) }
                    return
                }
                if (response.body()?.status != null && response.body()?.status == AppConstant.STATUS_SUCCESS) {
                    responseListener.onSuccess(response.body()?.data!![0])
                } else {
                    responseListener.onError(response.body()!!.error)
                }
            }
        })
    }
}