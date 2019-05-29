package com.communityx.models.login

data class Session(
    val device_token: Any,
    val expires: String,
    val id: String,
    val ip_address: String,
    val role: String,
    val session_id: String,
    val user_agent: String,
    val user_id: String
)