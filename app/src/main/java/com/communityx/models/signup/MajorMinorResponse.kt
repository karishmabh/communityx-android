package com.communityx.models.signup

import com.communityx.models.Error

data class MajorMinorResponse(
    val data: List<MinorsData>,
    val error: Error,
    val status: String
)