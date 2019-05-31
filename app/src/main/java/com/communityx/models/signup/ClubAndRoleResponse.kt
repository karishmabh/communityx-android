package com.communityx.models.signup

data class ClubAndRoleResponse(
    val data: List<ClubAndRoleData>,
    val error: Error,
    val status: String
)