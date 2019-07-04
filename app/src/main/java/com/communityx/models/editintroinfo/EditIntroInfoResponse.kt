package com.communityx.models.editintroinfo

import com.communityx.models.editinfo.Error

data class EditIntroInfoResponse(
        val `data`: List<String>,
        val error: Error,
        val status: String
)