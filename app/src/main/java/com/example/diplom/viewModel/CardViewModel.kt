package com.example.diplom.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.diplom.model.Card
import com.example.diplom.model.CardDataBase
import com.example.diplom.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardViewModel(application: Application): AndroidViewModel(application){

    private val readAllData: LiveData<List<Card>>
    private val repository: Repository

    init{
        val cardDao = CardDataBase.getDataBaseInstance(application).getCardDao()
        repository = Repository(cardDao)
        readAllData = repository.readAllData
    }

    // observable
    fun readAllData(): LiveData<List<Card>> {
        return readAllData
    }

    fun addNote(card: Card){
        viewModelScope.launch(Dispatchers.IO){
            repository.addNote(card)
        }
    }

    fun deleteNote(card: Card){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteNote(card)
        }
    }

    fun updateNote(card: Card){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateNote(card)
        }
    }
}