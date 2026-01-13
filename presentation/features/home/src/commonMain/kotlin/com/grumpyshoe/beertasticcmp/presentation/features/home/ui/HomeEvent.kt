package com.grumpyshoe.beertasticcmp.presentation.features.home.ui

import androidx.compose.runtime.Immutable

/**
 * This sealed class contains all the events that can be
 * triggered from HomeViewModel.
 */
@Immutable
sealed class HomeEvent {
    data class NavigateToDetails(val beerId: Int) : HomeEvent()
}