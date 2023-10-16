package com.softpie.karabiner.ui.main

import android.util.Log
import androidx.compose.runtime.Stable
import com.softpie.karabiner.ui.base.BaseViewModel
import com.softpie.karabiner.ui.root.NavGroup
import com.softpie.karabiner.utiles.TAG
import kotlinx.coroutines.flow.MutableStateFlow

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