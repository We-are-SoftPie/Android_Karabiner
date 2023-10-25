package com.softpie.karabiner.ui.setting.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.softpie.karabiner.component.loading.LoadInFullScreen
import com.softpie.karabiner.component.modifier.karaClickable
import com.softpie.karabiner.component.theme.BoldBody
import com.softpie.karabiner.component.theme.BoldTitle
import com.softpie.karabiner.component.theme.KarabinerColor
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.Label
import com.softpie.karabiner.ui.root.NavGroup

@Composable
fun ProfileScreen(
    vm: ProfileViewModel = viewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    val profileState = vm.uiState.collectAsState().value
    LaunchedEffect(key1 = true) {
        vm.init(context)
        vm.load()
    }
    AnimatedVisibility(
        visible  = profileState.loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LoadInFullScreen()
    }
    AnimatedVisibility(
        visible = profileState.loading.not(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            BoldTitle(
                text = "내 프로필"
            )
            Spacer(modifier = Modifier.height(48.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
            ) {
                Label(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .drawBehind {
                            val strokeWidthPx = 1.dp.toPx()
                            val verticalOffset = size.height - 2.sp.toPx()
                            drawLine(
                                color = KarabinerColor.Gray400,
                                strokeWidth = strokeWidthPx,
                                start = Offset(0f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        }.karaClickable {
                            navController.navigate(NavGroup.Setting.PROFILE_EDIT.id)
                        },
                    text = "수정하기",
                    textColor = KarabinerTheme.color.Gray400
                )
                BoldBody(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = "이름"
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Label(
                text = profileState.name,
                textColor = KarabinerTheme.color.Gray400
            )
            Spacer(modifier = Modifier.height(28.dp))
            BoldBody(text = "전화번호")
            Spacer(modifier = Modifier.height(4.dp))
            Label(
                text = profileState.tel,
                textColor = KarabinerTheme.color.Gray400
            )
            Spacer(modifier = Modifier.height(28.dp))
            BoldBody(text = "이메일")
            Spacer(modifier = Modifier.height(4.dp))
            Label(
                text = profileState.email,
                textColor = KarabinerTheme.color.Gray400
            )
            Spacer(modifier = Modifier.height(28.dp))


        }
    }
}