package com.grumpyshoe.beertasticcmp.data.source.preferences.di

import com.grumpyshoe.beertasticcmp.data.source.preferences.PreferenceServiceImpl
import com.grumpyshoe.beertasticcmp.data.source.preferences.PrefsDataStore
import com.grumpyshoe.beertasticcmp.data.source.preferences.SharedPreferenceService
import com.grumpyshoe.beertasticcmp.data.source.preferences.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module


val sharedPreferencesModule: Module = module {
    single<PrefsDataStore> {
        createDataStore()
    }
    single<SharedPreferenceService> {
        PreferenceServiceImpl(get(), get())
    }
}