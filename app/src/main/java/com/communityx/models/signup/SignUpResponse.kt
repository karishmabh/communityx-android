package com.communityx.models.signup

import com.communityx.models.Error

data class SignUpResponse(
    var data: List<SignUpData>,
    var error: Error,
    var status: String
)