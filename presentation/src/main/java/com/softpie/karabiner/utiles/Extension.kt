package com.softpie.karabiner.utiles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.room.TypeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


internal fun String.isValidPhoneNumber(): Boolean {
    val regex = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}\$".toRegex()
    return regex.matches(this)
}

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9\\._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$".toRegex()
    return emailRegex.matches(this)
}

internal fun Context.shortToast(content: String) =
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show()

internal fun Context.longToast(content: String) =
    Toast.makeText(this, content, Toast.LENGTH_LONG).show()

@Composable
internal fun rememberKeyboardIsOpen(): State<Boolean> {
    val view = LocalView.current

    return produceState(initialValue = view.isKeyboardOpen()) {
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            value = view.isKeyboardOpen()
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)

        awaitDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}
fun View.isKeyboardOpen(): Boolean {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return keypadHeight > screenHeight * 0.15
}

internal fun ViewModel.launchMain(action: () -> Unit) {
    viewModelScope.launch(Dispatchers.Main) {
        action()
    }
}

internal fun ViewModel.launchIO(action: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        action()
    }
}

@Composable
internal fun <SIDE_EFFECT : Any> Flow<SIDE_EFFECT>.collectAsSideEffect(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    sideEffect: (suspend (sideEffect: SIDE_EFFECT) -> Unit)
) {
    val sideEffectFlow = this
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(sideEffectFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            sideEffectFlow.collect {
                sideEffect(it)
            }
        }
    }
}

fun Int.getCategoryColor(): Pair<Color, Color> =
    when (this) {
        in 0..4 -> Pair(Color(0xFFFFE1E1), Color(0xFFFF0000))
        5 -> Pair(Color(0xFFFFCFEF), Color(0xFFFF00A8))
        in 6..9 -> Pair(Color(0xFFBCCBFF), Color(0xFF0B40FF))
        in 10..12 -> Pair(Color(0xFEF5C5), Color(0xFFFFB800))
        13 -> Pair(Color(0xFFDEE3FF), Color(0xFF2605F0))
        else -> Pair(Color(0xFFFFCFEF), Color(0xFFFF00A8))
    }

fun Int.getCategoryName(): String =
    when (this) {
        0 -> "불법광고물"
        1 -> "자전거/이륜차 방치 및 불편"
        2 -> "쓰레기, 폐기물"
        3 -> "해양쓰레기"
        4 -> "불법 숙박"
        5 -> "기타 생활불편"
        6 -> "교통위반"
        7 -> "이륜차 위반"
        8 -> "적재물 추락방지, 중량∙용량 위반"
        9 -> "버스전용차로 위반"
        10 -> "번호판 규정 위반"
        11 -> "불법등화, 반사판(지) 가림∙손상"
        12 -> "불법 튜닝, 해체, 조작"
        13 -> "기타 자동차 안전기준 위반"
        else -> "이외"
    }

fun LocalDateTime.toDateString(): String {
    val format = DateTimeFormatter.ofPattern("yyyy/MM/d")
    return this.format(format)
}

fun String.getCategoryNumber(): Int =
    when (this) {
        "불법광고물" -> 0
        "자전거/이륜차 방치 및 불편" -> 1
        "쓰레기, 폐기물" -> 2
        "해양쓰레기" ->3
        "불법 숙박" -> 4
        "기타 생활불편" -> 5
        "교통위반" -> 6
        "이륜차 위반" -> 7
        "적재물 추락방지, 중량∙용량 위반" -> 8
        "버스전용차로 위반" -> 9
        "번호판 규정 위반" -> 10
        "불법등화, 반사판(지) 가림∙손상" -> 11
        "불법 튜닝, 해체, 조작" -> 12
        "기타 자동차 안전기준 위반" -> 13
        else -> 30
    }

fun Bitmap.toByteArray() : ByteArray{
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 20, outputStream)
    return outputStream.toByteArray()
}

// ByteArray -> Bitmap 변환
fun ByteArray.toBitmap() : Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}