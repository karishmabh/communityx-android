package com.communityx.models.signup

data class RoleResponse(
    val `data`: List<RoleData>,
    val error: Error,
    val status: String
)