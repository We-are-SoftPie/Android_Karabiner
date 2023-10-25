package com.softpie.karabiner.ui.setting.profile.edit

import android.content.Context
import com.softpie.karabiner.local.sharedpreferences.KarabinerSharedPreferences
import com.softpie.karabiner.ui.base.BaseViewModel
import com.softpie.karabiner.ui.log.LogSideEffect
import com.softpie.karabiner.utiles.isValidEmail
import com.softpie.karabiner.utiles.isValidPhoneNumber
import com.softpie.karabiner.utiles.launchIO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class EditViewModel: BaseViewModel() {
    private lateinit var karabinerSharedPreferences: KarabinerSharedPreferences

    private val _uiState = MutableStateFlow(EditState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<EditSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun init(context: Context) {
        karabinerSharedPreferences = KarabinerSharedPreferences(context)
    }

    fun load() {
        _uiState.value = _uiState.value.copy(
            name = karabinerSharedPreferences.myName,
            email = karabinerSharedPreferences.myEmail,
            tel = karabinerSharedPreferences.myTel,
            loading = false
        )
    }

    fun save() {
        with(_uiState.value) {
            if (name.isNullOrBlank()) {
                postErrorEvent("이름을 입력해주세요")
                return
            }
            if (tel.isValidPhoneNumber().not()) {
                postErrorEvent("유효한 전화번호 입력해주세요")
                return
            }
            if (email.isValidEmail().not()) {
                postErrorEvent("유효한 이메일을 입력해주세요")
                return
            }
            karabinerSharedPreferences.myName = name
            karabinerSharedPreferences.myEmail = email
            karabinerSharedPreferences.myTel = tel
            launchIO {
                _sideEffect.send(EditSideEffect.SuccessSave)
            }
        }
    }

    private fun postErrorEvent(content: String) = launchIO {
        _sideEffect.send(EditSideEffect.FailedSave(content))
    }


    fun changeName(content: String) {
        _uiState.value = _uiState.value.copy(
            name = content,
        )
    }

    fun changeEmail(content: String) {
        _uiState.value = _uiState.value.copy(
            email = content,
        )
    }

    fun changeTel(content: String) {
        _uiState.value = _uiState.value.copy(
            tel = content
        )
    }
}