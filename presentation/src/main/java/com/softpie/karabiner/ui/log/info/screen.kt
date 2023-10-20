package com.softpie.karabiner.ui.log.info

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnitType.Companion.Sp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.softpie.karabiner.R
import com.softpie.karabiner.component.button.KarabinerButton
import com.softpie.karabiner.component.loading.LoadInFullScreen
import com.softpie.karabiner.component.modifier.karaClickable
import com.softpie.karabiner.component.theme.Body
import com.softpie.karabiner.component.theme.BoldBody
import com.softpie.karabiner.component.theme.BoldHeadline
import com.softpie.karabiner.component.theme.BoldTitle
import com.softpie.karabiner.component.theme.Headline
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.KarabinerTypography
import com.softpie.karabiner.component.theme.Label
import com.softpie.karabiner.component.theme.Title
import com.softpie.karabiner.component.theme.gradient
import com.softpie.karabiner.ui.log.LogScreen
import com.softpie.karabiner.ui.log.LogSideEffect
import com.softpie.karabiner.ui.log.LogViewModel
import com.softpie.karabiner.utiles.collectAsSideEffect
import com.softpie.karabiner.utiles.getCategoryColor
import com.softpie.karabiner.utiles.getCategoryName
import com.softpie.karabiner.utiles.shortToast
import com.softpie.karabiner.utiles.toDateString

//@Preview
//@Composable
//private fun Preview() {
//    KarabinerTheme {
//        LogInfoScreen()
//    }
//}

@Composable
fun LogInfoScreen(
    navController: NavController,
    logInfoViewModel: LogInfoViewModel = viewModel(),
    id: Int = 0
) {
    val focus = LocalFocusManager.current
    val context = LocalContext.current

    val logState = logInfoViewModel.uiState.collectAsState().value

    logInfoViewModel.sideEffect.collectAsSideEffect {
        when (it) {
            is LogInfoSideEffect.LoadError -> {
                context.shortToast(
                    it.exception.message ?: context.getString(R.string.text_loadError)
                )
                Log.e("HomeScreenError", it.exception.stackTraceToString())

            }
        }
    }
    LaunchedEffect(true) {
        logInfoViewModel.init(context)
        logInfoViewModel.load(id)
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
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Image(
                            modifier = Modifier
                                .width(9.dp)
                                .height(16.dp)
                                .align(CenterVertically)
                                .clickable {
                                    navController.popBackStack()
                                },
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "뒤로가기버튼"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        BoldTitle(text = "신고 상세보기")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Label(text = logState.data.date.toDateString())
                Spacer(modifier = Modifier.height(4.dp))
                BoldHeadline(text = logState.data.title)
                Spacer(modifier = Modifier.height(28.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = KarabinerTheme.color.Gray100
                )
                Spacer(modifier = Modifier.height(28.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        BoldBody(text = "카테고리")
                        Spacer(modifier = Modifier.height(4.dp))
                        val color = logState.data.category.getCategoryColor()
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = color.first
                        ) {
                            Label(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                text = logState.data.category.getCategoryName(),
                                textColor = color.second
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        BoldBody(text = "접수번호")
                        Spacer(modifier = Modifier.height(4.dp))
                        Label(
                            text = logState.data.declarationId,
                            textColor = KarabinerTheme.color.Gray400
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
                BoldBody(text = "설명")
                Spacer(modifier = Modifier.height(4.dp))
                Label(
                    modifier = Modifier.fillMaxWidth(),
                    text = "설명",
                    textColor = KarabinerTheme.color.Gray400
                )
                Spacer(modifier = Modifier.height(28.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = KarabinerTheme.color.Gray100
                )
                Spacer(modifier = Modifier.height(28.dp))
                BoldBody(text = "이미지")
                Spacer(modifier = Modifier.height(7.dp))
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = BitmapPainter(logState.data.image!!.asImageBitmap()),
                    contentDescription = "촬영된 이미지"
                )

            }
        }
    }
}


