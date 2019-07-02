package com.communityx.models.profile

import java.io.Serializable

data class Error(
    val error_code: Any,
    val error_message: Any
) :Serializable