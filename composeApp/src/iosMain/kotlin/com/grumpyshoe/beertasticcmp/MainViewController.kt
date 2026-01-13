package com.grumpyshoe.beertasticcmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.window.ComposeUIViewController
import com.grumpyshoe.beertasticcmp.data.repository.di.BeerDataModule
import com.grumpyshoe.beertasticcmp.domain.beer.di.BeerDomainModule
import org.koin.compose.KoinApplication


fun MainViewController() = ComposeUIViewController {
    KoinApplication(application = {
        modules(
            BeerDomainModule +
                    BeerDataModule +
                    appModule,
        )
    }) {
        MaterialTheme {
            App()
        }
    }
}