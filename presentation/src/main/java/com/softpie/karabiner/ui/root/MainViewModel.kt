package com.softpie.karabiner.ui.root

import androidx.compose.runtime.Stable
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

sealed class MainSideEffect() {
    object MainCameraTouchEvent: MainSideEffect()
}

class MainViewModel: BaseViewModel() {
    val state = MutableStateFlow(MainState())

    private val _sideEffect = Channel<MainSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun updateSelectedTab(it: NavGroup.Main) {
        state.value = state.value.copy(selectedTab = it)
    }

    fun clickSideEffect() {
        launchIO {
            _sideEffect.send(MainSideEffect.MainCameraTouchEvent)
        }
    }
}