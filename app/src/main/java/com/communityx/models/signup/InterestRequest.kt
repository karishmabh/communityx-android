package com.communityx.models.signup

data class InterestRequest(
    val interests: List<String>,
    val user_id: String,
    val suggested_interests: List<String>
)