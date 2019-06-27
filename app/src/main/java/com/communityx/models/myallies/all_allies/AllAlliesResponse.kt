package com.communityx.models.myallies.all_allies

data class AllAlliesResponse(
    val `data`: List<Data>,
    val error: Error,
    val status: String
)