package com.grumpyshoe.beertasticcmp.presentation.features.details.ui.state

import com.grumpyshoe.beertasticcmp.presentation.features.details.ui.uimodel.BeerDetailUIItem

data class DetailsViewState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val isFavorite: Boolean = false,
    val beerDetails: BeerDetailUIItem? = null
)