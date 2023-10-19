package com.softpie.karabiner.remote.request

import com.google.gson.annotations.SerializedName

data class KarabinerInputRequest(
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("address")
    val address: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("content")
    val content: String,
    @field:SerializedName("phoneNo")
    val phoneNo: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String,
)