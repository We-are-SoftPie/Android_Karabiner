package com.softpie.karabiner.utiles


internal fun String.isValidPhoneNumber(): Boolean {
    val regex = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}\$".toRegex()
    return regex.matches(this)
}