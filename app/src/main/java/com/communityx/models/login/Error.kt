package com.communityx.models.login

data class Error (
    val error_code: Any,
    val error_message: List<String>
)