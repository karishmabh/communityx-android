package com.communityx.models.connect_allies

data class ConnectAlliesResponse(
        val `data`: List<Data>,
        val error: Error,
        val status: String
)