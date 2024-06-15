package com.example.diplom.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cards_table")
data class Card(
    @PrimaryKey(autoGenerate = true)

    val id: Int,

    val firstName: String,
    val secondName: String,
    val thirdName: String,

    val post: String,

    val address: String,

    val number: String,
    val mail:String

): Parcelable
