package com.grumpyshoe.beertasticcmp

import android.app.Application
import com.grumpyshoe.beertasticcmp.data.repository.di.BeerDataModule
import com.grumpyshoe.beertasticcmp.domain.beer.di.BeerDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            modules(
                BeerDomainModule +
                        BeerDataModule +
                        appModule,
            )
        }
    }
}
