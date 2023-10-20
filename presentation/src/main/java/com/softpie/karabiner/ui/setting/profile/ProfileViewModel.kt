package com.softpie.karabiner.ui.setting.profile

import android.content.Context
import com.softpie.karabiner.local.sharedpreferences.KarabinerSharedPreferences
import com.softpie.karabiner.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel: BaseViewModel() {
    private lateinit var karabinerSharedPreferences: KarabinerSharedPreferences

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

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
}