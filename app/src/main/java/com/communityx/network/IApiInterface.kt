package com.communityx.network

import com.communityx.models.oauth.OAuthRequest
import com.communityx.models.oauth.OAuthResponse
import com.communityx.models.signup.MajorMinorResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IApiInterface {

    @POST("basic-auth")
    fun getBasicAuth(@Body oAuthRequest: OAuthRequest): Call<OAuthResponse>

    @GET("major")
    fun getMajorMinor(): Call<MajorMinorResponse>
}