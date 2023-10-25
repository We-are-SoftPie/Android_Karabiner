package com.softpie.karabiner.remote.response.karabiner

import com.google.gson.annotations.SerializedName

data class ImageResponse (
    @field:SerializedName("tag")
    val tag: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("content")
    val content: String
)