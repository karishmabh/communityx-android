package com.communityx.models.signup.club

import com.communityx.models.signup.DataX
import com.communityx.models.signup.Error

data class ClubResponse(
    val `data`: List<DataX>,
    val error: Error,
    val status: String
)