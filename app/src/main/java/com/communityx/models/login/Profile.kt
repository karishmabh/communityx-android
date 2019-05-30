package com.communityx.models.login

data class Profile(
    val admin: List<Admin>,
    val student: List<Student>,
    val professional: List<Professional>,
    val organisation: List<Organisation>,
    val category: String,
    val email: String,
    val id: String,
    val last_login: String,
    val phone: String
)