package com.communityx.models.signup

import com.communityx.models.Error

data class VerificationResponse (
    val data: List<String>,
    val error: Error,
    val status: String
)