package com.softpie.karabiner.ui.cam

import com.softpie.karabiner.local.room.DeclarationModel
import com.softpie.karabiner.remote.model.ImageModel


data class CamState (
    val data: ImageModel = ImageModel(0, "", ""),

    var nowPage: Int = 0,
    var textPage: Int = 0
)


sealed class CamSideEffect() {
    object SuccessResultPost : CamSideEffect()
    data class LoadFailed(val throwable: Throwable): CamSideEffect()

}