package com.communityx.models.profile

import java.io.Serializable

data class ProfileResponse(
    val `data`: List<Data>,
    val error: Error,
    val status: String
): Serializable