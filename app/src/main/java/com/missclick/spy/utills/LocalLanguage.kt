package com.missclick.spy.utills

import android.content.res.Resources
import java.util.*

sealed class LocalLanguage {
    object Russian : LocalLanguage()
    object English : LocalLanguage()
    object Ukrainian : LocalLanguage()

    companion object{
        fun mapLangToString(language: LocalLanguage) : String{
            return when(language){
                is LocalLanguage.Russian -> "ru"
                is LocalLanguage.English -> "en"
                is LocalLanguage.Ukrainian -> "ua"
            }
        }

        fun mapStringToLang(lang : String) : LocalLanguage {
            return when(lang){
                "ru", "Russian" -> LocalLanguage.Russian
                "en", "English" -> LocalLanguage.English
                    //"English" -> LocalLanguage.English
                "ua", "Ukrainian" -> LocalLanguage.Ukrainian
                else -> LocalLanguage.English
            }
        }

        fun changeLocale(res : Resources, language: LocalLanguage){
            println("language")
            println(mapLangToString(language))
            val conf = res.configuration
            val locale = Locale(mapLangToString(language))
            Locale.setDefault(locale)
            conf.setLocale(locale)
            res.updateConfiguration(conf, null)
        }
    }
}

