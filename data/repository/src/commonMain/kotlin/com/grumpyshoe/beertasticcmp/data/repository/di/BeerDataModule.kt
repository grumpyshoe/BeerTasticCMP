package com.grumpyshoe.beertasticcmp.data.repository.di

import com.grumpyshoe.beertasticcmp.data.repository.BeerRepositoryImpl
import com.grumpyshoe.beertasticcmp.data.source.network.di.networkModule
import com.grumpyshoe.beertasticcmp.data.source.preferences.di.sharedPreferencesModule
import com.grumpyshoe.beertasticcmp.domain.beer.repository.BeerRepository
import org.koin.dsl.module

val BeerDataModule =
    buildList {
        add(sharedPreferencesModule)
        add(
            module {
                single<BeerRepository> { BeerRepositoryImpl(get(), get()) }
            },
        )
        add(networkModule)
    }
