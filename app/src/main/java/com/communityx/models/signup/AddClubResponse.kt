package com.communityx.models.signup

data class AddClubResponse(
    val `data`: List<Data>,
    val error: Error,
    val status: String
)