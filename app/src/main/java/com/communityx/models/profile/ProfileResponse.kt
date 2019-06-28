package com.communityx.models.profile

data class ProfileResponse(
    val `data`: List<Data>,
    val error: Error,
    val status: String
)