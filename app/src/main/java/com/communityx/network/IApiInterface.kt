package com.communityx.network

import com.communityx.models.oauth.OAuthRequest
import com.communityx.models.oauth.OAuthResponse
import com.communityx.models.signup.MajorMinorResponse
import com.communityx.models.signup.OtpRequest
import com.communityx.models.signup.OtpResponse
import com.communityx.models.signup.VerifyOtpRequest
import com.communityx.models.signup.image.ImageUploadRequest
import com.communityx.models.signup.image.ImageUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface IApiInterface {

    @POST("basic-auth")
    fun getBasicAuth(@Body oAuthRequest: OAuthRequest): Call<OAuthResponse>

    @GET("major")
    fun getMajorMinor(@Header("token") token:String): Call<MajorMinorResponse>

    @POST("otp")
    fun generateOtp(@Header("token") token: String, @Body otpRequest: OtpRequest): Call<OtpResponse>

    @POST("otp-verify")
    fun verifyOtp(@Header("token") token: String, @Body verifyOtpRequest: VerifyOtpRequest): Call<OtpResponse>

    @Multipart
    @POST("upload")
    fun uploadImage(@Header("token") token: String, @Part image: MultipartBody.Part, @Part type: MultipartBody.Part): Call<ImageUploadResponse>
}