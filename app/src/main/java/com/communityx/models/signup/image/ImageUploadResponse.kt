package com.communityx.models.signup.image

import com.communityx.models.Error

data class ImageUploadResponse(
    val data: List<Data>,
    val error: Error,
    val status: String
)