package com.softpie.karabiner.ui.setting.profile.edit

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.softpie.karabiner.R
import com.softpie.karabiner.component.button.KarabinerButton
import com.softpie.karabiner.component.loading.LoadInFullScreen
import com.softpie.karabiner.component.textfield.KarabinerTextField
import com.softpie.karabiner.component.theme.BoldBody
import com.softpie.karabiner.component.theme.BoldTitle
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.local.sharedpreferences.KarabinerSharedPreferences
import com.softpie.karabiner.ui.root.NavGroup
import com.softpie.karabiner.utiles.collectAsSideEffect
import com.softpie.karabiner.utiles.rememberKeyboardIsOpen
import com.softpie.karabiner.utiles.shortToast

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditScreen(
    vm: EditViewModel = viewModel(),
    navController: NavController,
    bottomVisible: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    val focus = LocalFocusManager.current
    val keyboardShow by rememberKeyboardIsOpen()

    val state = vm.uiState.collectAsState().value
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    vm.sideEffect.collectAsSideEffect() {
        when (it) {
            is EditSideEffect.SuccessSave -> {
                context.shortToast("저장에 성공하였습니다.")
                navController.navigate(NavGroup.Setting.PROFILE.id) {
                    popUpTo(NavGroup.Setting.PROFILE_EDIT.id) { inclusive = true}
                }
                bottomVisible(true)
            }
            is EditSideEffect.FailedSave -> {
                 context.shortToast(content = it.content)
            }

            else -> {}
        }
    }
    DisposableEffect(key1 = lifecycleOwner.value) {
        onDispose {
            bottomVisible(true)
        }
    }
    bottomVisible(false)
    LaunchedEffect(key1 = true) {
        vm.init(context)
        vm.load()
    }
    AnimatedVisibility(
        visible  = state.loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LoadInFullScreen()
    }
    AnimatedVisibility(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focus.clearFocus()
                })
            },
        visible = state.loading.not(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.padding(start = 24.dp)
            ) {
                Image(
                    modifier = Modifier
                        .width(9.dp)
                        .height(16.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            navController.navigate(NavGroup.Setting.PROFILE.id) {
                                popUpTo(NavGroup.Setting.PROFILE_EDIT.id) { inclusive = true }
                            }
                        },
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "뒤로가기버튼"
                )
                Spacer(modifier = Modifier.width(8.dp))
                BoldTitle(text = "프로필 수정")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                BoldBody(text = "이름")
                Spacer(modifier = Modifier.height(4.dp))
                KarabinerTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.name,
                    onValueChange = {
                        vm.changeName(it)
                    }
                )
                Spacer(modifier = Modifier.height(28.dp))
                BoldBody(text = "전화번호")
                Spacer(modifier = Modifier.height(4.dp))
                KarabinerTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.tel,
                    onValueChange = {
                        vm.changeTel(it)
                    }
                )
                Spacer(modifier = Modifier.height(28.dp))
                BoldBody(text = "이메일")
                Spacer(modifier = Modifier.height(4.dp))
                KarabinerTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.email,
                    onValueChange = {
                        vm.changeEmail(it)
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            KarabinerButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 16.dp,
                    ),
                text = "저장하기",
                shape = if (keyboardShow) RoundedCornerShape(0.dp) else KarabinerTheme.shape.semiLarge
            ) {
                vm.save()
            }
        }
    }

}