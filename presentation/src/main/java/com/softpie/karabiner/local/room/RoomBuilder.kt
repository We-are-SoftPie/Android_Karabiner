package com.softpie.karabiner.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DeclarationEntity::class], version = 2)
@TypeConverters(RoomTypeConverter::class)
abstract class KarabinerDatabase: RoomDatabase() {
    abstract fun declarationDao(): DeclarationDao

    companion object {
        private var instance: KarabinerDatabase? = null

        @Synchronized
        fun getInstance(context: Context): KarabinerDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    KarabinerDatabase::class.java, "karabiner_database"
                ).build()
            }
            return instance
        }
    }

}