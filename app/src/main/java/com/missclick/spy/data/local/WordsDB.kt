package com.missclick.spy.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.missclick.spy.data.local.entities.WordEntitity

@Database(entities = [WordEntitity::class], version = 3)
abstract class WordsDB : RoomDatabase(){
    abstract fun dao() : WordsDao

}


