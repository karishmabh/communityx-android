package com.communityx.models.myallies.all_allies

data class Data(
    val `data`: List<DataX>,
    val current_page: Int,
    val first_page_url: String,
    val from: Any,
    val last_page: Int,
    val last_page_url: String,
    val next_page_url: Any,
    val path: String,
    val per_page: Int,
    val prev_page_url: Any,
    val to: Any,
    val total: Int
)