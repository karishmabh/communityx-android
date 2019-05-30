package com.communityx.models.signup

data class StudentSignUpRequest(
    var club_name: String? = null,
    var club_role: String? = null,
    var dob: String? = null,
    var email: String? = null,
    var full_name: String? = null,
    var interests: MutableList<String>? = mutableListOf(),
    var password: String? = null,
    var phone: String? = null,
    var postal_code: String? = null,
    val role: String,
    var standard: String? = null,
    var standard_name: String? = null,
    var standard_year: String? = null,
    var profile_image: String? = "default_image.jpeg",
    var company_name: String? =null,
    var job_title: String? =null,

    var organization_name: String? = null,
    var organization_website: String? = null
)