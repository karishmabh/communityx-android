package com.communityx.models.logout

import com.communityx.models.Error

data class LogoutResponse(
        val data: List<String>,
        val error: Error,
        val status: String
)