package com.grumpyshoe.beertasticcmp.data.source.network.beer.di

import com.grumpyshoe.beertasticcmp.data.source.network.beer.api.PunkAPI
import com.grumpyshoe.beertasticcmp.data.source.network.beer.datasource.BeerRemoteDatasource
import com.grumpyshoe.beertasticcmp.data.source.network.beer.datasource.BeerRemoteDatasourceImpl
import org.koin.dsl.module

internal val BeerNetworkModule =
    module {

        single<PunkAPI> { PunkAPI() }

        factory<BeerRemoteDatasource> {
            BeerRemoteDatasourceImpl(get())
        }
    }
