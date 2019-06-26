package com.communityx.models.myallies.invitation

data class AlliesInvitationResponse(
    val `data`: List<Data>,
    val error: Error,
    val status: String
)