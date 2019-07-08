package com.communityx.models.signup.institute

data class InstituteRequest(
        val institute: String,
        val role: String?,
        val type: String,
        val user_id: String
)