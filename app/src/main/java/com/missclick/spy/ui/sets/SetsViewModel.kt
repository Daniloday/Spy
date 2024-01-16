package com.missclick.spy.ui.sets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.missclick.spy.data.IRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class SetsViewModel(
        private val repository: IRepository
) : ViewModel() {

    fun getSets() = liveData<List<String>>(Dispatchers.IO) {
        try {
            emit(repository.getSets())
        } catch (e : Exception){
            e.printStackTrace()
        }

    }
}
