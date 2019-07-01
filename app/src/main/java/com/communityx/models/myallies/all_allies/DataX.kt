package com.communityx.models.myallies.all_allies

import com.communityx.models.myallies.invitation.Interest

data class DataX(
    val city: String,
    val education: List<Education>,
    val email: String,
    val id: String,
    val last_login: String,
    val name: String,
    val phone: String,
    val profile: Profile,
    val state: String,
    val type: String,
    val work_experience: List<WorkExperience>,
    val interests: List<Interest>
)