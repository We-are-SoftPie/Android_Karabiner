package com.softpie.karabiner.ui.main

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softpie.karabiner.R
import com.softpie.karabiner.component.appbar.KarabinerBottomNavigation
import com.softpie.karabiner.component.button.KarabinerBottomButton
import com.softpie.karabiner.component.button.KarabinerIconBottomButton
import com.softpie.karabiner.component.modifier.karaOuterShadow
import com.softpie.karabiner.component.theme.KarabinerColor
import com.softpie.karabiner.ui.root.NavGroup
import com.softpie.karabiner.utiles.TAG

@Composable
fun MainBottomNavigation(
    selectedTab: NavGroup.Main,
    selectedTabCallback: (NavGroup.Main) -> Unit,
    camCallback: () -> Unit
) {
    val items = listOf(
        NavGroup.Main.LIST,
        NavGroup.Main.CAM,
        NavGroup.Main.SET,
    )
    val scale by animateFloatAsState(targetValue = if (selectedTab == NavGroup.Main.CAM) 1.1f else 1f, label = "")

    Box {
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
                if (item == NavGroup.Main.CAM)
                    KarabinerBottomButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .alpha(0f),
                        text = "",
                        karabinerable = selected,
                        iconId = item.icon,
                        enable = false,
                    ) {}
                else
                    KarabinerBottomButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        text = "se",
                        onClick = {
                            selectedTabCallback(item)
                        },
                        karabinerable = selected,
                        iconId = item.icon
                    )
            }
        }
        Row {
            Spacer(modifier = Modifier.weight(1f))
            KarabinerIconBottomButton(
                modifier = Modifier
                    .offset(y = (-10).dp)
                    .scale(scale),
                iconId = R.drawable.ic_cam,
                karabinerable = selectedTab == NavGroup.Main.CAM
            ) {
                if (selectedTab == NavGroup.Main.CAM)
                    Log.d(TAG, "MainBottomNavigation: ")
                selectedTabCallback(NavGroup.Main.CAM)
            }
            Spacer(modifier = Modifier.weight(1f))
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
    }) {}
}