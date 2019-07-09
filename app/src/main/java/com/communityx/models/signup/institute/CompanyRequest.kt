package com.communityx.models.signup.institute

data class CompanyRequest(
    val company: String,
    val role: String,
    val user_id: String,

    val user_company_id :String="",
    val address : String= "",
    val latitude : String = "",
    val longitude : String = "",
    val start_date : String = "",
    val end_date : String = "",
    val description : String = "",
    val is_current : String = ""

)