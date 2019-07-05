package com.communityx.models.editintroinfo

data class EditIntroInfoRequest (
        val first_name: String,
        val headline: String,
        val interests: MutableList<String>?,
        val last_name: String,
        val latitude: String,
        val longitude: String,
        val type: String,
        val user_id: String,
        val suggested_interests: MutableList<String>?,
        val profile_image :String?
)