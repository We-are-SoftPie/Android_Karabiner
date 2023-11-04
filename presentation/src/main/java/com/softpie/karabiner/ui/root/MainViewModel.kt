package com.softpie.karabiner.ui.root

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.softpie.karabiner.ui.base.BaseViewModel
import com.softpie.karabiner.ui.cam.CamSideEffect
import com.softpie.karabiner.utiles.launchIO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

@Stable
data class MainState(
    var selectedTab: NavGroup.Main = NavGroup.Main.LIST
)

class MainViewModel: BaseViewModel() {
    val state = MutableStateFlow(MainState())

    fun updateSelectedTab(it: NavGroup.Main) {
        state.value = state.value.copy(selectedTab = it)
    }
}