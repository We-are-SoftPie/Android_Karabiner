package com.softpie.karabiner.remote.response.karabiner

import com.google.gson.annotations.SerializedName

data class InputResponse (
    @field:SerializedName("reportId")
    val reportId: String,
    @field:SerializedName("status")
    val status: String,
)