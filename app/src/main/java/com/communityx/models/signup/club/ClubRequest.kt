package com.communityx.models.signup.club

import com.communityx.models.signup.ClubData

data class ClubRequest (val user_id: String, val club: List<ClubData>)