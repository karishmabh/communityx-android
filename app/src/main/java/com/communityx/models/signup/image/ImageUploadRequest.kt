package com.communityx.models.signup.image

import okhttp3.MultipartBody
import retrofit2.http.Part

data class ImageUploadRequest(
    @Part
    val image: MultipartBody.Part,
    val type: MultipartBody.Part
)