package com.softpie.karabiner.utiles

import android.content.Context
import android.widget.Toast


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