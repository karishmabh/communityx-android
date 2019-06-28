package com.communityx.models.profile

data class Data(
    val clubs: List<Education>,
    val dob: String,
    val education: Education,
    val email: String,
    val first_name: String,
    val id: String,
    val interests: List<Education>,
    val last_name: String,
    val phone: String,
    val postal_code: String,
    val profile_image: String,
    val type: String,
    val work_experience: List<Education>
)