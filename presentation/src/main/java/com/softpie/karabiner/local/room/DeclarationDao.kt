package com.softpie.karabiner.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeclarationDao {
    @Query("SELECT * FROM declaration_table")
    fun getMembers(): DeclarationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(entity: DeclarationEntity)

    @Update
    fun updateMember(entity: DeclarationEntity)

    @Delete
    fun deleteMember(entity: DeclarationEntity)
}