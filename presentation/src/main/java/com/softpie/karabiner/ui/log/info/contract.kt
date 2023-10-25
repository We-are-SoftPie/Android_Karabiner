package com.softpie.karabiner.ui.log.info

import android.graphics.Bitmap
import androidx.core.graphics.createBitmap
import com.softpie.karabiner.local.room.DeclarationModel
import java.time.LocalDateTime

data class LogInfoState(
    var data: DeclarationModel = DeclarationModel(0, null, "", "", 0, "", "", LocalDateTime.MIN),

    val loading: Boolean = true,
)

sealed class LogInfoSideEffect {
    data class LoadError(val exception: Throwable) : LogInfoSideEffect()

}