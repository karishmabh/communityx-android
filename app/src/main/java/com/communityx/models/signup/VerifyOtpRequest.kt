package com.communityx.models.signup

data class VerifyOtpRequest(
    val otp: String,
    val phone: String
)