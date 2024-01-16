package com.missclick.spy.data.local.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//todo add Language
@Entity(tableName = "db")
data class WordEntitity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int? = null,

    @ColumnInfo(name = "word")
    var word : String,

    @ColumnInfo(name = "category")
    var category : String
)