package com.softpie.karabiner.local.room

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.time.LocalDateTime

data class DeclarationModel(
    val id: Int = 0,
    val image: Bitmap?,
    val title: String,
    val description: String,
    val category: Int,
    val declarationId: String,
    val location: String,
    val date: LocalDateTime,
)
