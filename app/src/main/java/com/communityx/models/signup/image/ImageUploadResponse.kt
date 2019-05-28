package com.communityx.models.signup.image

data class ImageUploadResponse(
    val `data`: List<Any>,
    val error: Error,
    val status: String
)