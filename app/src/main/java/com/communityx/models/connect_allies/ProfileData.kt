package com.communityx.models.connect_allies

data class ProfileData(
        val category: String,
        val email: String,
        val id: String,
        val last_login: String,
        val phone: String,
        val profile: Profile,
        val minors: List<Minors>
)