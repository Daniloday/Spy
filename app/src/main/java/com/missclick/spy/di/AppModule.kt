package com.missclick.spy.di

import com.missclick.spy.ui.cards.CardsViewModel
import com.missclick.spy.ui.menu.MenuViewModel
import com.missclick.spy.ui.sets.SetsViewModel
import com.missclick.spy.ui.settings.SettingsFragment
import com.missclick.spy.ui.settings.SettingsViewModel
import com.missclick.spy.ui.splash.SplashViewModel
import com.missclick.spy.ui.words.WordsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MenuViewModel(get(),get())
    }
    viewModel {
        CardsViewModel(get())
    }
    viewModel {
        SplashViewModel(get(), get())
    }
    viewModel {
        SetsViewModel(get())
    }
    viewModel {
        WordsViewModel(get(),get())
    }
    viewModel {
        SettingsViewModel(get(),get(), get())
    }
}