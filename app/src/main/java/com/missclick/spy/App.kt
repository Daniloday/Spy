package com.missclick.spy

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.missclick.spy.di.appModule
import com.missclick.spy.di.dataModule
import com.missclick.spy.di.provideDb
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(
            this
        ) { }
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(appModule, dataModule)
        }

    }
}