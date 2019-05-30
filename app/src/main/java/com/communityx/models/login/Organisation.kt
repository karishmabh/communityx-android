package com.communityx.models.login

data class Organisation(
        val id: String,
        val interests: List<Interest>,
        val name: String,
        val postal_code: String,
        val profile_image: String,
        val user_id: String,
        val website: String
)