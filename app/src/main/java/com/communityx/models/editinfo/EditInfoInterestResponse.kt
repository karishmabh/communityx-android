package com.communityx.models.editinfo

data class EditInfoInterestResponse(
    val `data`: List<Data>,
    val error: Error,
    val status: String
)