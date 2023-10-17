package com.softpie.karabiner.remote.model

import com.google.gson.annotations.SerializedName

data class ImageModel(
    val tag: Int,
    val title: String,
    val content: String
)