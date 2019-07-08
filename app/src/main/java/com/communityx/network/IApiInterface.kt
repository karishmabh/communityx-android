package com.communityx.network

import com.communityx.models.connect_allies.ConnectAlliesResponse
import com.communityx.models.editinfo.EditInfoInterestResponse
import com.communityx.models.editintroinfo.EditIntroInfoRequest
import com.communityx.models.editintroinfo.EditIntroInfoResponse
import com.communityx.models.headline.EditHeadlineRequest
import com.communityx.models.job_companies.JobResponse
import com.communityx.models.login.LoginRequest
import com.communityx.models.login.LoginResponse
import com.communityx.models.logout.LogoutResponse
import com.communityx.models.myallies.all_allies.AllAlliesResponse
import com.communityx.models.myallies.all_allies.UpdateInvitationRequest
import com.communityx.models.myallies.invitation.AlliesInvitationResponse
import com.communityx.models.oauth.OAuthRequest
import com.communityx.models.oauth.OAuthResponse
import com.communityx.models.profile.ProfileResponse
import com.communityx.models.signup.*
import com.communityx.models.signup.cause.CauseRequest
import com.communityx.models.signup.club.ClubRequest
import com.communityx.models.signup.club.ClubResponse
import com.communityx.models.signup.image.ImageUploadResponse
import com.communityx.models.signup.institute.CompanyRequest
import com.communityx.models.signup.institute.InstituteRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface IApiInterface {

    @POST("basic-auth")
    fun getBasicAuth(@Body oAuthRequest: OAuthRequest): Call<OAuthResponse>

    @GET("interests")
    fun getMajorMinor(@Header("token") token: String): Call<MajorMinorResponse>

    @GET("interests")
    fun getInterests(@Header("token") token: String): Call<EditInfoInterestResponse>

    @GET("user/logout")
    fun logout(@Header("token") token: String, @Header("session") session: String): Call<LogoutResponse>

    @POST("login")
    fun doLogin(@Header("token") token: String, @Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("send-otp")
    fun generateOtp(@Header("token") token: String, @Body otpRequest: OtpRequest): Call<OtpResponse>

    @POST("verify-otp")
    fun verifyOtp(@Header("token") token: String, @Body verifyOtpRequest: VerifyOtpRequest): Call<OtpResponse>

    @Multipart
    @POST("upload")
    fun uploadImage(@Header("token") token: String, @Part image: MultipartBody.Part, @Part type: MultipartBody.Part): Call<ImageUploadResponse>

    @POST("signup")
    fun signUp(@Header("token") token: String, @Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @POST("signup-student")
    fun signUpStudent(@Header("token") token: String, @Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @POST("signup-professional")
    fun signUpProfessional(@Header("token") token: String, @Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @POST("signup-organisation")
    fun signUpOrg(@Header("token") token: String, @Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @GET("clubs")
    fun getClubsAndRoles(@Header("token") token: String, @Query("q") query: String): Call<ClubAndRoleResponse>

    @GET("causes")
    fun getCausesAndRoles(@Header("token") token: String,@Query("q") query: String): Call<ClubAndRoleResponse>

    @GET("roles")
    fun getRoles(@Header("token") token: String, @Query("type") type: String): Call<RoleResponse>

    @GET("user/friend-suggest-list")
    fun getConnectingAllies(@Header("token") token: String, @Header("session") session: String): Call<ConnectAlliesResponse>

    @GET("job-titles")
    fun getJobTitles(@Header("token") token: String, @Query("q") query: String): Call<JobResponse>

    @GET("companies")
    fun getCompanies(@Header("token") token: String, @Query("q") query: String): Call<JobResponse>

    @GET("institute")
    fun getStandardList(@Header("token") token: String, @Query("type") type: String, @Query("q") query: String) : Call<StandardResponse>

    @GET("user/profile")
    fun getProfile(@Header("token") token: String, @Header("session") session: String) : Call<ProfileResponse>

    @POST("verify-user")
    fun verifyUser(@Header("token") token: String, @Header("session") session: String, @Body emailPhoneVerificationRequest: EmailPhoneVerificationRequest) : Call<VerificationResponse>

    //mock apis
    @GET("user/allies")
    fun getAllAllies(@Header("token") token: String, @Header("session") session: String): Call<AllAlliesResponse>

    @GET("user/allies/invitations")
    fun getAlliesInvitations(@Header("token") token: String, @Header("session") session: String): Call<AllAlliesResponse>

    @GET("user/allies/suggestions")
    fun getAlliesSuggestions(@Header("token") token: String, @Header("session") session: String): Call<AllAlliesResponse>

    @FormUrlEncoded
    @POST("user/allies")
    fun sendInvitation(@Header("token") token: String, @Header("session") session: String, @Field("send_to_id") userId: String): Call<LogoutResponse>

    @PUT("user/allies")
    fun updateInvitation(@Header("token") token: String, @Header("session") session: String, @Body updateInvitationRequest: UpdateInvitationRequest): Call<LogoutResponse>

    @POST("user-club")
    fun addUserClub(@Header("token") token: String, @Body clubRequest: ClubRequest): Call<ClubResponse>

    @POST("user-cause")
    fun addUserCause(@Header("token") token: String, @Body causeRequest: CauseRequest): Call<ClubResponse>

    @POST("user-institute")
    fun addUserInstitute(@Header("token") token: String, @Body instituteRequest: InstituteRequest): Call<ClubResponse>

    @POST("user-company")
    fun addUserCompany(@Header("token") token: String, @Body companyRequest: CompanyRequest): Call<ClubResponse>

    @POST("user-interest")
    fun addUserInterest(@Header("token") token: String, @Body interestRequest: InterestRequest): Call<ClubResponse>

    @POST("suggest-interest")
    fun suggestInterest(@Header("token") token: String, @Body interestRequest: InterestRequest): Call<ClubResponse>

    @POST("suggest-interest")
    fun suggestInterest(@Header("token") token: String, @Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @PUT("user/update-user")
    fun updateIntroInfo(@Header("token") token: String, @Header("session") session: String, @Body editIntroInfoRequest: EditIntroInfoRequest) : Call<EditIntroInfoResponse>

    @PUT("user/update-headline")
    fun updateHeadline(@Header("token") token: String, @Header("session") session: String, @Body editHeadlineRequest: EditHeadlineRequest): Call<EditIntroInfoResponse>

}