package com.communityx.models.login

data class LoginResponse(
    val data: List<Data>,
    val error: Error,
    val status: String
)