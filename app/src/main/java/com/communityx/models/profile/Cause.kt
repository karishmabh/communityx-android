package com.communityx.models.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

data class Cause (

    var id: String,
    var name: String,
    var description: Any
)
