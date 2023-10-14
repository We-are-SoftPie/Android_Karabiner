package com.softpie.karabiner.local.room

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "declaration_table")
data class DeclarationEntity (
    @PrimaryKey()
    val id: Int = 0,

    @ColumnInfo(name = "image")
    val image: Bitmap,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "category")
    val category: Int,

    @ColumnInfo(name = "declarationId")
    val declarationId: String,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "date")
    val date: LocalDateTime,
)