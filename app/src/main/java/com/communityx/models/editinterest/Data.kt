package com.communityx.models.editinterest

data class Data(
    val editsubinterests: List<Editsubinterest>,
    val id: String,
    val name: String,
    val subinterests: List<Subinterest>
)