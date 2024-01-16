package com.missclick.spy.di

import android.content.Context
import androidx.room.Room
import com.missclick.spy.data.IRepository
import com.missclick.spy.data.Repository
import com.missclick.spy.data.local.ILocalDataSource
import com.missclick.spy.data.local.WordsDB
import com.missclick.spy.data.local.LocalDataSource
import com.missclick.spy.data.local.SettingsRepository
import com.missclick.spy.utills.SetsManager
import org.koin.dsl.module

val dataModule = module {
    single {
        provideDb(get())
    }
    single {
        provideLocalDataSource(get())
    }
    single {
        provideRepository(get())
    }
    single {
        SettingsRepository(get())
    }
    single {
        SetsManager()
    }
}

fun provideDb(context: Context) : WordsDB {
    return Room.databaseBuilder(context, WordsDB::class.java, "db")
        //.fallbackToDestructiveMigration()
        .build()
}

fun provideLocalDataSource(wordsDB: WordsDB) : ILocalDataSource{
    return LocalDataSource(wordsDB)
}

fun provideRepository(localDataSource: ILocalDataSource): IRepository {
    return Repository(localDataSource)
}