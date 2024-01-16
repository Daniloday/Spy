package com.missclick.spy.ui.settings

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.data.IRepository
import com.missclick.spy.data.Repository
import com.missclick.spy.data.local.SettingsRepository
import com.missclick.spy.data.models.WordsModel
import com.missclick.spy.utills.LocalLanguage
import com.missclick.spy.utills.SetsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.*

class SettingsViewModel(
        private val settingsRepository: SettingsRepository,
        private val repository: IRepository,
        private val setsManager: SetsManager)
    : ViewModel() {

    val initManager = MutableLiveData<Boolean>()

    val language = settingsRepository.language

    fun setLanguage(language: LocalLanguage, setName : String){
        viewModelScope.launch(Dispatchers.IO) {
            val lang = LocalLanguage.mapLangToString(language)
            settingsRepository.setLanguage(lang)
            settingsRepository.setSet(setName)
            updateDB()
        }
    }

    private suspend fun updateDB(){
        setsManager.getKeys().forEach {
            Log.e("Key", it)
            repository.removeWordInCategory(it)
        }
        initManager.postValue(true)
    }

    fun preloadDb(collections : List<WordsModel>){
        viewModelScope.launch {
            collections.map {
                async(Dispatchers.IO) {
                    repository.insertWord(it)
                }
            }.awaitAll()
        }
    }
}