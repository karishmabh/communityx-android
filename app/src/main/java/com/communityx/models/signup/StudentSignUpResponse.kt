package com.communityx.models.signup

data class StudentSignUpResponse(
    val `data`: List<Any>,
    val error: Error,
    val status: String
)