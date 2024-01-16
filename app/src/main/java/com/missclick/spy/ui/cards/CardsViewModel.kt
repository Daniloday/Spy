package com.missclick.spy.ui.cards

import android.util.Log
import androidx.lifecycle.*
import com.missclick.spy.data.IRepository
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import java.lang.Exception

class CardsViewModel(private val repository: IRepository) : ViewModel() {

    val cardState = MutableLiveData<CardState>(CardState.ClosedCard(1))

    //old default mode

    fun getRandomWord(category : String) = liveData<String>(Dispatchers.IO){
        try {
            val words = repository.getWordsFromCategoryByName(nameOfCategory = category)
            println("word")
            println(words)
            emit(words[(words.indices).random()])
        } catch (ex : Exception){
            ex.printStackTrace()
        }

    }
    //new hard mode
//    fun getRandomWord(category : String) = liveData(Dispatchers.IO){
//        try {
//            val words = repository.getWordsFromCategoryByName(nameOfCategory = category)
//            val indices = (words.indices).shuffled()
//            val word1 = words[indices[0]]
//            val word2 = words[indices[1]]
//            emit(listOf(word1,word2))
//        } catch (ex : Exception){
//            ex.printStackTrace()
//        }
//
//    }

    fun getSpy(players : Int) =
        (1..players).random()

    fun getSpies(players : Int, spies : Int) =
        (1..players).shuffled().take(spies)

    fun changeState(players: Int){
        if (cardState.value is CardState.ClosedCard){
            cardState.value = CardState.OpenedCard((cardState.value as CardState).number)
        } else {
            val number = (cardState.value as CardState).number
            if(number >= players) cardState.value = CardState.EndCard
            else cardState.value = CardState.ClosedCard(number + 1)
        }
    }



}

sealed class CardState(val number : Int){
    class ClosedCard(number : Int) : CardState(number)
    class OpenedCard(number : Int) : CardState(number)
    object EndCard : CardState(0)
}