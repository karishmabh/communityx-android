package com.communityx.models.signup

data class ClubAndRoleData(
    val clubs: List<Club>,
    val causes: List<Causes>,
    val roles: List<String>
)