package com.softpie.karabiner.ui.main

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.softpie.karabiner.component.appbar.KarabinerBottomNavigation
import com.softpie.karabiner.component.button.KarabinerBottomButton
import com.softpie.karabiner.ui.root.NavGroup

@Composable
fun MainBottomNavigation(
    selectedTab: NavGroup.Main,
    selectedTabCallback: (NavGroup.Main) -> Unit
) {
    val items = listOf(
        NavGroup.Main.LIST,
        NavGroup.Main.CAM,
        NavGroup.Main.SET,
    )

    KarabinerBottomNavigation(
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .padding(vertical = 4.dp)
    ) {
        items.forEach { item ->
            val selected = item == selectedTab
            val transition = updateTransition(targetState = selected, label = "dividerAnimation")
            val animatedOpacity by transition.animateFloat(
                transitionSpec = { tween(durationMillis = 500) },
                label = "opacity"
            ) {
                if (it) 1f else 0f
            }
            KarabinerBottomButton(
                text = "se",
                onClick = {
                    selectedTabCallback(item)
                }
            )
        }
    }
}
