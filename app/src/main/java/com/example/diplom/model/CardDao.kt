package com.example.diplom.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardDao{
    @Insert
    suspend fun addNote(card: Card)

    @Delete
    suspend fun deleteNote(card: Card)

    @Query("SELECT * FROM cards_table ORDER by id ASC")
    fun readAllData(): LiveData<List<Card>>

    // запрос на апдейт данных
    @Update
    suspend fun updateNote(card: Card)
}