package com.example.diplom.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Card::class], version = 1, exportSchema = false)
abstract class CardDataBase: RoomDatabase() {

    abstract fun getCardDao(): CardDao

    companion object{
        @Volatile
        private var INSTANCE: CardDataBase? = null

        fun getDataBaseInstance(context: Context): CardDataBase{
            val tempInst = INSTANCE

            if(tempInst!= null){
                return tempInst
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardDataBase::class.java,
                    "notes_table")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}