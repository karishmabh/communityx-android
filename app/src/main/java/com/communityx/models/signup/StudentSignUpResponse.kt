package com.communityx.models.signup

data class StudentSignUpResponse(
    val data: List<StudentData>,
    val error: Error,
    val status: String
)