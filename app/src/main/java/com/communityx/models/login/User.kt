package com.communityx.models.login

import com.communityx.models.myallies.all_allies.Education
import com.communityx.models.myallies.all_allies.WorkExperience
import com.communityx.models.myallies.invitation.Interest

data class User(
    val type: String,
    val name: String,
    val email: String,
    val id: String,
    val last_login: String,
    val phone: String,
    val profile: Profile,
    val city: String,
    val education: List<Education>,
    val state: String,
    val work_experience: List<WorkExperience>,
    val interests: List<Interest>
)