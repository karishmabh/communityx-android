package com.communityx.models.signup

import com.communityx.models.Error

data class SignUpResponse(
    val data: List<SignUpData>,
    val error: Error,
    val status: String
)