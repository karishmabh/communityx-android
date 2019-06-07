package com.communityx.models.job_companies

import com.communityx.models.Error

data class JobResponse(
    val `data`: List<List<Data>>,
    val error: Error,
    val status: String
)