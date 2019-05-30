package com.communityx.models.logout

import com.communityx.models.login.Error

data class LogoutResponse(
        val data: List<String>,
        val error: Error,
        val status: String
)