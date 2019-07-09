package com.communityx.models.profile

import java.io.Serializable

data class Education(
    var datatype : String,
    val id: String,
    val name: String,
    val role: String,
    val type: String,

    val start_date :String="",
    val end_date :String=""
) : Serializable
