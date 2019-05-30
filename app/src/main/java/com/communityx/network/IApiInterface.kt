package com.communityx.network

import com.communityx.models.login.LoginRequest
import com.communityx.models.login.LoginResponse
import com.communityx.models.logout.LogoutResponse
import com.communityx.models.oauth.OAuthRequest
import com.communityx.models.oauth.OAuthResponse
import com.communityx.models.signup.*
import com.communityx.models.signup.image.ImageUploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface IApiInterface {

    @POST("basic-auth")
    fun getBasicAuth(@Body oAuthRequest: OAuthRequest): Call<OAuthResponse>

    @GET("major")
    fun getMajorMinor(@Header("token") token: String): Call<MajorMinorResponse>

    @GET("user/logout")
    fun logout(@Header("token") token: String, @Header("session") session: String): Call<LogoutResponse>

    @POST("login")
    fun doLogin(@Header("token") token: String, @Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("otp")
    fun generateOtp(@Header("token") token: String, @Body otpRequest: OtpRequest): Call<OtpResponse>

    @POST("otp-verify")
    fun verifyOtp(@Header("token") token: String, @Body verifyOtpRequest: VerifyOtpRequest): Call<OtpResponse>

    @Multipart
    @POST("upload")
    fun uploadImage(@Header("token") token: String, @Part image: MultipartBody.Part, @Part type: MultipartBody.Part): Call<ImageUploadResponse>

    @POST("signup")
    fun signUp(@Header("token") token: String, @Body studentSignUpRequest: SignUpRequest) : Call<SignUpResponse>
}