package com.softpie.karabiner.utiles

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalView


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