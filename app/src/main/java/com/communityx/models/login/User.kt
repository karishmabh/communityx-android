package com.communityx.models.login

data class User(
    val category: String,
    val email: String,
    val id: String,
    val last_login: String,
    val phone: String,
    val profile: Profile
)