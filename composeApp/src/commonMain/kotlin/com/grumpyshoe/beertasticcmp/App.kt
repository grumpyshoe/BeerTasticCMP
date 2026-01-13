package com.grumpyshoe.beertasticcmp

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.grumpyshoe.beertasticcmp.navigation.NavigationGraph
import com.grumpyshoe.beertasticcmp.presentation.common.resources.AppTheme
import com.grumpyshoe.beertasticcmp.presentation.features.details.viewmodel.DetailsViewModel
import com.grumpyshoe.beertasticcmp.presentation.features.home.viewmodel.HomeViewModel
import com.grumpyshoe.beertasticcmp.presentation.features.splashscreen.viewmodel.SplashscreenViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
    viewModelOf(::SplashscreenViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()

@Composable
@Preview
fun App() {

    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    AppTheme {
        NavigationGraph()
    }
}