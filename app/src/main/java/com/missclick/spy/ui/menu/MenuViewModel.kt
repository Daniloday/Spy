package com.missclick.spy.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.missclick.spy.data.IRepository
import com.missclick.spy.data.Repository
import com.missclick.spy.data.local.SettingsRepository
import com.missclick.spy.data.models.GameParams
import kotlinx.coroutines.launch

class MenuViewModel(private val repository: IRepository, private val settingsRepository: SettingsRepository) : ViewModel() {

    val players = settingsRepository.players
    val spy = settingsRepository.spies
    val time = settingsRepository.timer
    val set = settingsRepository.set

    fun setGameParams(gameParams: GameParams){
        viewModelScope.launch {
            settingsRepository.setPlayers(gameParams.players)
            settingsRepository.setSpies(gameParams.spy)
            settingsRepository.setTimer(gameParams.time)
            settingsRepository.setSet(gameParams.category)
        }
    }
}