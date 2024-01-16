package com.missclick.spy.data.local

import android.util.Log
import com.missclick.spy.data.local.ILocalDataSource
import com.missclick.spy.data.local.WordsDB
import com.missclick.spy.data.local.entities.WordEntitity

class LocalDataSource(private val wordsDB: WordsDB) : ILocalDataSource{
    override suspend fun addWord(word : WordEntitity) =
        wordsDB.dao().insert(word)

    override suspend fun getWords(category : String): List<WordEntitity> {
        return wordsDB.dao().getWordsByCategory(category = category)
    }

    override suspend fun getSets(): List<String> {
        return wordsDB.dao().getSets()
    }

    override suspend fun removeWordsInCategory(category: String) {
        wordsDB.dao().removeAllCategory(category)
    }

    override suspend fun removeWord(word: WordEntitity) {
        wordsDB.dao().remove(word = word.word, category = word.category)
        Log.e(word.word, word.category)
    }

}