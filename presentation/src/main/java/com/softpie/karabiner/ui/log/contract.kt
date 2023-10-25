package com.softpie.karabiner.ui.log

import com.softpie.karabiner.local.room.DeclarationModel

data class LogState(
    val name: String = "",
    val data: List<DeclarationModel> = emptyList(),

    val loading: Boolean = true,
)

sealed class LogSideEffect {
    data class LoadError(val exception: Throwable) : LogSideEffect()

}