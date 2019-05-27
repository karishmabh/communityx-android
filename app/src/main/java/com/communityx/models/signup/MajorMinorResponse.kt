package com.communityx.models.signup

data class MajorMinorResponse(
    val `data`: List<List<MinorsData>>,
    val error: Error,
    val status: String
)