package com.communityx.models.signup

data class StandardResponse(
    val `data`: List<StandardData>,
    val error: Error,
    val status: String
)