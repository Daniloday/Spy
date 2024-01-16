package com.missclick.spy.data.local

import com.missclick.spy.data.local.entities.WordEntitity

interface ILocalDataSource {
    suspend fun addWord(word : WordEntitity) : Long
    suspend fun getWords(category : String) : List<WordEntitity>
    suspend fun getSets() : List<String>
    suspend fun removeWordsInCategory(category: String)
    suspend fun removeWord(word : WordEntitity)
}