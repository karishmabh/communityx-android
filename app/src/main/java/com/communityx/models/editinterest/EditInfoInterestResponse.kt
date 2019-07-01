package com.communityx.models.editinterest

data class EditInfoInterestResponse(
    val `data`: List<Data>,
    val error: Error,
    val status: String
)