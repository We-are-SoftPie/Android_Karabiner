package com.softpie.karabiner.component.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val bottomNavHeight = 64.dp

@Composable
fun KarabinerBottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = backgroundColor,
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(bottomNavHeight)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceAround,
            content = content,
        )
    }
}
