package com.communityx.models.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

data class ProfileData (

    var id: String,
    var name: Any,
    var phone: String,
    var email: String,
    var category: String,
    var lastLogin: String,
    var profile: Profile
)
