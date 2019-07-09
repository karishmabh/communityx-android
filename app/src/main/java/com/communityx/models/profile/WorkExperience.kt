package com.communityx.models.profile

data class WorkExperience(
    val datatype : String = "we",
    val id: String,
    val name: String,
    val role: String,
    val start_date :String,
    val end_date :String
)