package com.communityx.models.signup.cause

import com.communityx.models.signup.CauseData

data class CauseRequest (val user_id: String, val cause: List<CauseData>)