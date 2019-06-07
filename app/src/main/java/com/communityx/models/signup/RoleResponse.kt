package com.communityx.models.signup

data class RoleResponse(
    val `data`: List<List<RoleData>>,
    val error: Error,
    val status: String
)