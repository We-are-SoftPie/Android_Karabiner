package com.softpie.karabiner.ui.setting.profile.edit

data class EditState (
    val name: String = "",
    val tel: String = "",
    val email: String = "",

    val loading: Boolean = true
)


sealed class EditSideEffect {
    object SuccessSave: EditSideEffect()
    data class FailedSave(val content: String): EditSideEffect()
}