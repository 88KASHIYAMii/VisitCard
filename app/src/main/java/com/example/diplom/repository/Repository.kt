package com.example.diplom.repository

import androidx.lifecycle.LiveData
import com.example.diplom.model.Card
import com.example.diplom.model.CardDao

class Repository(private val cardDao: CardDao){

    val readAllData: LiveData<List<Card>> = cardDao.readAllData()

    suspend fun addNote(card: Card){
        cardDao.addNote(card)
    }

    suspend fun deleteNote(card: Card){
        cardDao.deleteNote(card)
    }

    suspend fun updateNote(card: Card){
        cardDao.updateNote(card)
    }

}