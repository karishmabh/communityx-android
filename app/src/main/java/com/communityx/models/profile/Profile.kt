package com.communityx.models.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

data class Profile (
    var id: String,
    var firstName: String,
    var lastName: String,
    var postalCode: String,
    var dob: String,
    var jobTitle: ProfileJobTitle,
    var companyName: String,
    var profileImage: String,
    var causeRole: String,
    var fullName: String,
    var interests: List<ProfileInterest>,
    var cause: Cause,
    var company: ProfileCompany
)
