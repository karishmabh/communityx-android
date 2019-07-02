package com.communityx.models.profile

import java.io.Serializable

data class profile(
        val dob: String,
        val first_name: String,
        val id: String,
        val last_name: String,
        val postal_code: String,
        val profile_image: String,
        val user_id: String
) : Serializable