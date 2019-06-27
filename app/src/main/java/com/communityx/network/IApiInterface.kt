package com.communityx.network

import com.communityx.models.connect_allies.ConnectAlliesResponse
import com.communityx.models.job_companies.JobResponse
import com.communityx.models.login.LoginRequest
import com.communityx.models.login.LoginResponse
import com.communityx.models.logout.LogoutResponse
import com.communityx.models.myallies.all_allies.AllAlliesResponse
import com.communityx.models.myallies.invitation.AlliesInvitationResponse
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
    fun signUp(@Header("token") token: String, @Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @GET("clubs")
    fun getClubsAndRoles(@Header("token") token: String, @Query("q") query: String): Call<ClubAndRoleResponse>

    @GET("causes")
    fun getCausesAndRoles(@Header("token") token: String,@Query("q") query: String): Call<ClubAndRoleResponse>

    @GET("roles")
    fun getRoles(@Header("token") token: String): Call<RoleResponse>

    @GET("user/friend-suggest-list")
    fun getConnectingAllies(@Header("token") token: String, @Header("session") session: String): Call<ConnectAlliesResponse>

    @GET("job-titles")
    fun getJobTitles(@Header("token") token: String, @Query("q") query: String): Call<JobResponse>

    @GET("companies")
    fun getCompanies(@Header("token") token: String, @Query("q") query: String): Call<JobResponse>

    @GET("standard")
    fun getStandardList(@Header("token") token: String, @Query("standard") type: String, @Query("q") query: String) : Call<StandardResponse>

    @POST("verify-user")
    fun verifyUser(@Header("token") token: String, @Header("session") session: String, @Body emailPhoneVerificationRequest: EmailPhoneVerificationRequest) : Call<VerificationResponse>

    //mock apis
    @GET("user/allies")
    fun getAllAllies(@Header("token") token: String, @Header("session") session: String): Call<AllAlliesResponse>

    @GET("user/allies/invitations")
    fun getAlliesInvitations(@Header("token") token: String, @Header("session") session: String): Call<AlliesInvitationResponse>
}