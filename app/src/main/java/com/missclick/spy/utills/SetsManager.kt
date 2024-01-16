package com.missclick.spy.utills

import android.content.res.Resources
import com.missclick.spy.R
import com.missclick.spy.data.models.WordsModel

class SetsManager {

    private var sets  = mutableMapOf<String, Array<String>>()

    fun initSets(res : Resources){
        sets = mutableMapOf<String, Array<String>>()
        val wordsBasic = res.getStringArray(R.array.basic)
        val wordsCountries = res.getStringArray(R.array.countries)
        val wordsTransport = res.getStringArray(R.array.transport)


        sets[res.getString(R.string.basic)] = wordsBasic
        sets[res.getString(R.string.countries)] = wordsCountries
        sets[res.getString(R.string.transport)] = wordsTransport

    }

    fun getWords(): List<WordsModel> {
        var lists = mutableListOf<WordsModel>()
        sets.keys.forEach { key ->
            val a = sets[key]?.map {
                WordsModel(word = it, category = key)
            }?.toMutableList()
            lists = (lists + a!!).toMutableList()
        }
        return lists.toList()
    }

    fun getKeys() = sets.keys.toList()

}