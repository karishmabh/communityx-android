package com.communityx.models.login

data class Student(
        val club_name: String,
        val club_role: String,
        val dob: String,
        val full_name: String,
        val id: String,
        val interests: List<Interest>,
        val postal_code: String,
        val profile_image: String,
        val standard: String,
        val standard_name: String,
        val standard_year: String,
        val user_id: String
)