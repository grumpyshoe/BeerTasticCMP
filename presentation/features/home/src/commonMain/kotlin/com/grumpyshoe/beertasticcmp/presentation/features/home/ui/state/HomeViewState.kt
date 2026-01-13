package com.grumpyshoe.beertasticcmp.presentation.features.home.ui.state

import androidx.compose.runtime.Immutable
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.uimodel.BeerUIItem

@Immutable
data class HomeViewState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val randomBeer: BeerUIItem? = null,
    val favorites: List<BeerUIItem>? = null,
    val beerList: List<BeerUIItem> = emptyList(),
)