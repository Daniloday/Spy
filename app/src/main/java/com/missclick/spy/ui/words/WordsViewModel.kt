package com.missclick.spy.ui.words

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.missclick.spy.data.IRepository
import com.missclick.spy.data.local.SettingsRepository
import com.missclick.spy.data.models.WordListModel
import com.missclick.spy.data.models.WordsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.lang.Exception

class WordsViewModel(
        private val repository: IRepository,private val settingsRepository: SettingsRepository
) : ViewModel() {

    val set = settingsRepository.set

    fun getWords(setName : String) = liveData<List<String>>(Dispatchers.IO) {
        try {
            Log.e("Words", setName)
            emit(repository.getWordsFromCategoryByName(setName))
        } catch (e : Exception){
            e.printStackTrace()
        }

    }

    fun chooseSet(setName : String){
        viewModelScope.launch {
            settingsRepository.setSet(setName)
        }
    }

    fun updateSetName(oldSetName : String, newSetName : String,data : List<WordListModel>){
        if(oldSetName != newSetName) {
            removeWordsInCategory(oldSetName)
            addWords(data, newSetName)
        }
    }

    fun addWords(words : List<WordListModel>, category : String){
        viewModelScope.launch(Dispatchers.IO) {
            words.map {
                async { repository.insertWord(WordsModel(word = it.word, category = category)) }
            }.awaitAll()
        }
    }

    fun removeWordsInCategory(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeWordInCategory(category)
        }
    }

    fun removeWord(word : WordListModel, category : String){
        Log.e("RemoveWord", "${word.word}$category")
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeWord(WordsModel(word = word.word, category = category))
        }
    }
}