package com.communityx.network.serviceRepo

import android.content.Context
import com.communityx.models.signup.*
import com.communityx.models.signup.image.ImageUploadRequest
import com.communityx.models.signup.image.ImageUploadResponse
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
                responseListener.onError(t)
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

    fun generateOtp(context: Context, otpRequest: OtpRequest, responseListener: ResponseListener<String>) {
        DataManager.getService().generateOtp(AuthRepo.getAccessToken(context), otpRequest)
            .enqueue(object : Callback<OtpResponse> {

                override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                    responseListener.onError(t)
                }

                override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
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

    fun verifyOtp(context: Context, verifyOtpRequest: VerifyOtpRequest, responseListener: ResponseListener<String>) {
        DataManager.getService().verifyOtp(AuthRepo.getAccessToken(context), verifyOtpRequest)
            .enqueue(object : Callback<OtpResponse> {

                override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                    responseListener.onError(t)
                }

                override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
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

    fun uploadImage(context: Context, imageUploadRequest: ImageUploadRequest, responseListener: ResponseListener<ImageUploadResponse>) {
        DataManager.getService().uploadImage(AuthRepo.getAccessToken(context),imageUploadRequest.image, imageUploadRequest.type).enqueue(object :Callback<ImageUploadResponse>{
            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                responseListener.onError(t)
            }

            override fun onResponse(call: Call<ImageUploadResponse>, response: Response<ImageUploadResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { responseListener.onError(it) }
                    return
                }
                if (response.body()?.status != null && response.body()?.status == AppConstant.STATUS_SUCCESS) {
                    responseListener.onSuccess(response.body()!!)
                } else {
                    responseListener.onError(response.body()!!.error)
                }
            }

        })
    }

    fun createSignUp(
        context: Context,
        studentSignUpRequest: SignUpRequest,
        responseListener: ResponseListener<SignUpResponse>
    ) {
        DataManager.getService().signUp(AuthRepo.getAccessToken(context), studentSignUpRequest)
            .enqueue(object : Callback<SignUpResponse> {
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                responseListener.onError(t)
            }

            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (!response.isSuccessful) {
                    response.errorBody()?.let { responseListener.onError(it) }
                    return
                }
                if (response.body()?.status != null && response.body()?.status == AppConstant.STATUS_SUCCESS) {
                    responseListener.onSuccess(response.body()!!)
                } else {
                    responseListener.onError(response.body()!!.error)
                }
            }

        })
    }
}