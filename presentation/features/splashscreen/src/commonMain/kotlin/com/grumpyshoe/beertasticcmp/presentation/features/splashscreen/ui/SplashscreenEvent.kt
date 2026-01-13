package com.grumpyshoe.beertasticcmp.presentation.features.splashscreen.ui

import androidx.compose.runtime.Immutable

/**
 * This sealed class contains all the events that can be
 * triggered from HomeViewModel.
 */
@Immutable
sealed class SplashscreenEvent {
    data object NavigateToHome : SplashscreenEvent()
}