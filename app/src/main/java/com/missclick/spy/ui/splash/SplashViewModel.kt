package com.missclick.spy.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.missclick.spy.data.IRepository
import com.missclick.spy.data.local.SettingsRepository
import com.missclick.spy.data.models.WordsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception


class SplashViewModel(private val repository: IRepository,
                      private val settingsRepository: SettingsRepository)
    : ViewModel() {

    val ids = MutableLiveData<List<Long>>()
    val language = settingsRepository.language
    //val firstLaunch = settingsRepository.firstLaunch

    fun setFirstLaunch(status : Boolean){
        viewModelScope.launch {
            settingsRepository.setFirstLaunch(status)
        }
    }

    fun preloadDb(collections : List<WordsModel>){
        viewModelScope.launch {
            ids.value = collections.map {
                async(Dispatchers.IO) {
                    repository.insertWord(it)
                }
            }.awaitAll()
        }
    }

    fun getFirstLaunch() = liveData<Boolean>(Dispatchers.IO) {
        try {
            settingsRepository.firstLaunch.collect{
                val sets = repository.getSets()
                if(it) emit(true)
                if(!it && sets.isEmpty()) emit(true)
                else emit(false)
            }
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

}