package com.softpie.karabiner.remote.mapper

import com.softpie.karabiner.remote.model.ImageModel
import com.softpie.karabiner.remote.response.karabiner.ImageResponse

fun ImageResponse.toModel() =
    ImageModel(
        tag = tag.toInt(),
        title = title,
        content = content
    )