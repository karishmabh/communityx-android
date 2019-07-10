package com.communityx.models.signup

data class Error(
    val error_code: Int,
    val error_message: ArrayList<String>
)