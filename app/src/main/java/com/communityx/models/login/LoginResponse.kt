package com.communityx.models.login

import com.communityx.models.Error

data class LoginResponse(
    val data: List<Data>,
    val error: Error,
    val status: String
)