package com.softpie.karabiner.local.room

import com.softpie.karabiner.utiles.toBitmap

internal fun DeclarationEntity.toModel() =
    DeclarationModel(
        id = id,
        image = image,
        title = title,
        description = description,
        category = category,
        declarationId = declarationId,
        location = location,
        date = date
    )

internal fun List<DeclarationEntity>.toModels() =
    this.map {
        it.toModel()
    }