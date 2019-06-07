package com.communityx.models.signup

data class RoleResponseV2(
    val `data`: List<List<RoleData>>,
    val error: Error,
    val status: String
)