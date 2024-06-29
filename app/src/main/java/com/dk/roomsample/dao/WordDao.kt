package com.dk.roomsample.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dk.roomsample.entity.TextEntity
import com.dk.roomsample.entity.WordEntity

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table")
    fun getAllData(): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(textEntity: WordEntity)

    @Query("DELETE FROM word_table")
    fun deleteAllData()

}