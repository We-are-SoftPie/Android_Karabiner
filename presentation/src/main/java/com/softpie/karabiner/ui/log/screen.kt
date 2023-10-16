package com.softpie.karabiner.ui.log

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.softpie.karabiner.R
import com.softpie.karabiner.component.button.KarabinerButton
import com.softpie.karabiner.component.loading.LoadInFullScreen
import com.softpie.karabiner.component.theme.Headline
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.Label
import com.softpie.karabiner.component.theme.Title
import com.softpie.karabiner.ui.root.NavGroup
import com.softpie.karabiner.utiles.collectAsSideEffect
import com.softpie.karabiner.utiles.getCategoryColor
import com.softpie.karabiner.utiles.getCategoryName
import com.softpie.karabiner.utiles.shortToast
import com.softpie.karabiner.utiles.toDateString
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//@Preview
//@Composable
//private fun Preview() {
//    KarabinerTheme {
//        LogScreen()
//    }
//}

@Composable
fun LogScreen(
    navController: NavController,
    logViewModel: LogViewModel = viewModel(),
) {
    val focus = LocalFocusManager.current
    val context = LocalContext.current

    val logState = logViewModel.uiState.collectAsState().value

    logViewModel.sideEffect.collectAsSideEffect {
        when (it) {
            is LogSideEffect.LoadError -> {
                context.shortToast(
                    it.exception.message ?: context.getString(R.string.text_loadError)
                )
                Log.e("HomeScreenError", it.exception.stackTraceToString())

            }
        }
    }
    LaunchedEffect(true) {
        logViewModel.init(context)
        logViewModel.load()
    }
    AnimatedVisibility(
        visible  = logState.loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LoadInFullScreen()
    }
    AnimatedVisibility(
        visible = logState.loading.not(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 24.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focus.clearFocus()

                })
            }
        ) {
            item {
                Spacer(modifier = Modifier.height(36.dp))
                Headline(text = "${logState.name}님,")
                Row {
                    Title(text = "지금까지 ")
                    Title(text = logState.data.size.toString(), karabinerable = true)
                    Title(text = "건 신고하셨네요!")
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row {
                    KarabinerButton(text = "더미") {
                        val drawable = context.getDrawable(R.drawable.ic_logo)!!
                        val bitmap = drawable.toBitmap()
//                    val dummyIcon = BitmapFactory.decodeResource(context.resources!!, R.drawable.ic_logo)
                        Log.d("TAG", "LogScreen: $bitmap")
                        logViewModel.dummy(
                            bitmap
                        )
                    }
                    KarabinerButton(text = "새로고침") {
                        logViewModel.load()
                    }
                }
            }
            itemsIndexed(
                logState.data
            ) {index, item ->
                Column(
                    modifier = Modifier.clickable {
                        Log.d("TAG", "LogScreen: $index")
                        navController.navigate(
                            NavGroup.Main.LIST_IFNO.id.replace(
                                oldValue = "{id}",
                                newValue = "${item.id}"
                            )
                        )
                    }
                ) {
                    if (index == 0) {
                        Spacer(modifier = Modifier.height(1.dp))
                    } else {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            thickness = 1.dp,
                            color = KarabinerTheme.color.Gray100
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    val color = item.category.getCategoryColor()
                    Surface(
                        shape = KarabinerTheme.shape.semiMiddle,
                        color = color.first
                    ) {
                        Label(
                            modifier = Modifier.padding(horizontal = 4.39.dp, vertical = 3.dp),
                            text = item.category.getCategoryName(),
                            textColor = color.second
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Headline(
                        text = item.title,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Label(
                            modifier = Modifier.align(Alignment.CenterStart),
                            text = item.date.toDateString(),
                            textColor = Color(0xFF999999)
                        )
                        Label(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            text = item.declarationId,
                            textColor = Color(0xFF999999)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

