package com.communityx.models

data class SignUpRequest(
    var club_name: String? = null,
    var club_role: String? = null,
    var dob: String? = null,
    var email: String? = null,
    var full_name: String? = null,
    var interests: List<String>? = null,
    var password: String? = null,
    var phone: String? = null,
    var postal_code: String? = null,
    var role: String? = null,
    var standard: String? = null,
    var standard_name: String? = null,
    var standard_year: String? = null
)