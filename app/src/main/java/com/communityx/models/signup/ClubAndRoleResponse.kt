package com.communityx.models.signup

data class ClubAndRoleResponse(
    val data: List<Club>,
    val error: Error,
    val status: String
)