package com.grumpyshoe.beertasticcmp.data.source.network.di

import com.grumpyshoe.beertasticcmp.data.source.network.beer.di.BeerNetworkModule
import org.koin.dsl.module

internal val NetworkCoreModule =
    module {

//        single { HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) } }
//        single {
//            OkHttpClient
//                .Builder()
//                .addInterceptor(get<HttpLoggingInterceptor>())
//                .build()
//        }
//        single {
//            Retrofit
//                .Builder()
//                .client(get<OkHttpClient>())
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(BuildConfig.BASE_URL)
//                .build()
//        }
    }

val NetworkModule = NetworkCoreModule + BeerNetworkModule
