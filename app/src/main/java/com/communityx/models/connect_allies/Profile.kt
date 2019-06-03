package com.communityx.models.connect_allies

data class Profile(
        val club: Club,
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
        val name: String,
        val website: String,
        val job_title: String,
        val company_name: String
)