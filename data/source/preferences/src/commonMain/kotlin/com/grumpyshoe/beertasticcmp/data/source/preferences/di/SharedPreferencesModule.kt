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
//expect val sharedPreferencesModule: Module

// SharedPreferencesModule =
//    module {
//
//        single<SharedPreferences> {
//            val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
//            EncryptedSharedPreferences.create(
//                androidContext().packageName + "_secure_preferences",
//                masterKey,
//                androidContext(),
//                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
//            )
//        }
//    }