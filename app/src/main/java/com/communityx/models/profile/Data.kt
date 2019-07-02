package com.communityx.models.profile

import java.io.Serializable

data class Data(
    val clubs: List<Education>,
    val dob: String,
    val education: Education,
    val email: String,
    val first_name: String,
    val id: String,
    val interests: List<Education>,
    val profile : profile,
    val last_name: String,
    val phone: String,
    val postal_code: String,
    val profile_image: String,
    val type: String,
    val state:String,
    val city:String,
    val work_experience: List<Education>
) : Serializable
