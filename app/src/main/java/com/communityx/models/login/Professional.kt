package com.communityx.models.login

data class Professional(
        val club_name: String,
        val club_role: String,
        val company_name: String,
        val dob: String,
        val full_name: String,
        val id: String,
        val interests: List<Interest>,
        val job_title: String,
        val postal_code: String,
        val profile_image: String,
        val user_id: String
)