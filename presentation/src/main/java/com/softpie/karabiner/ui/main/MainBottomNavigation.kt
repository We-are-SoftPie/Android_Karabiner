package com.softpie.karabiner.ui.main

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softpie.karabiner.component.appbar.KarabinerBottomNavigation
import com.softpie.karabiner.component.button.KarabinerBottomButton
import com.softpie.karabiner.component.button.KarabinerButton
import com.softpie.karabiner.component.modifier.karaOuterShadow
import com.softpie.karabiner.component.theme.KarabinerColor
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

    Box {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            KarabinerButton(text = "") {
                
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        KarabinerBottomNavigation(
            backgroundColor = Color.White,
            modifier = Modifier
                .karaOuterShadow(
                    alpha = 0.05f,
                    color = KarabinerColor.Black,
                    shadowRadius = 4.dp
                )
        ) {
            items.forEach { item ->
                val selected = item == selectedTab
                KarabinerBottomButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    text = "se",
                    onClick = {
                        selectedTabCallback(item)
                    },
                    karabinerable = selected
                )
            }
        }
    }


}


@Preview
@Composable
fun BottomNavigation() {
    var selectedTab by remember {
        mutableStateOf<NavGroup.Main>(NavGroup.Main.LIST)
    }
    MainBottomNavigation(selectedTab = selectedTab, selectedTabCallback = {
        selectedTab = it
    })
}