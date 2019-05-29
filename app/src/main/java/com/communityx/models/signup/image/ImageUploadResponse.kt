package com.communityx.models.signup.image

data class ImageUploadResponse(
    val data: List<Data>,
    val error: Error,
    val status: String
)