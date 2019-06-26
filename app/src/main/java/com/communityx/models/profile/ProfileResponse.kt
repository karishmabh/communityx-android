package com.communityx.models.profile

import com.communityx.models.Error
import com.communityx.models.login.Data

data class ProfileResponse (
    val data: List<ProfileData>,
    val error: Error,
    val status: String
)