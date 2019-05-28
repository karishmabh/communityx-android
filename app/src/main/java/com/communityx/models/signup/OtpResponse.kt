package com.communityx.models.signup

data class OtpResponse(
    val `data`: List<String>,
    val error: Error,
    val status: String
)