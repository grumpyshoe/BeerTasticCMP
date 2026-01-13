package com.grumpyshoe.beertasticcmp.presentation.features.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grumpyshoe.beertasticcmp.domain.beer.models.Beer
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetBeerById
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.IsBeerFavorite
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.SetIsBeerFavorite
import com.grumpyshoe.beertasticcmp.domain.beer.utils.onError
import com.grumpyshoe.beertasticcmp.domain.beer.utils.onSuccess
import com.grumpyshoe.beertasticcmp.presentation.features.details.ui.DetailsAction
import com.grumpyshoe.beertasticcmp.presentation.features.details.ui.state.DetailsViewState
import com.grumpyshoe.beertasticcmp.presentation.features.details.ui.uimodel.BeerDetailUIItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val beerId: Int,
    private val getBeerById: GetBeerById,
    private val isBeerFavorite: IsBeerFavorite,
    private val setIsBeerFavorite: SetIsBeerFavorite,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _viewState = MutableStateFlow<DetailsViewState>(DetailsViewState(isLoading = true))
    val viewState = _viewState.asStateFlow()

    init {
        loadBeerDetails(beerId)
    }

    private fun loadBeerDetails(beerId: Int?) {

        if (beerId == null) {
            _viewState.update {
                it.copy(hasError = true)
            }
            return
        }

        viewModelScope.launch(ioDispatcher) {

            val isFavorite = isBeerFavorite(beerId)

            getBeerById(beerId)
                .onSuccess { beer ->
                    val detailsUIItem = beer.mapToUIModel()
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            hasError = false,
                            isFavorite = isFavorite,
                            beerDetails = detailsUIItem
                        )
                    }
                }.onError {
                    _viewState.update {
                        it.copy(
                            hasError = true
                        )
                    }
                }
        }
    }

    fun onAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.ToggleFavoriteState -> toggleFavorite()
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch(ioDispatcher) {
            val currentState = isBeerFavorite(beerId)
            setIsBeerFavorite(beerId, !currentState)

            if (viewState.value.beerDetails != null) {
                _viewState.update {
                    it.copy(isFavorite = !currentState)
                }
            }
        }
    }
}

private fun Beer.mapToUIModel(): BeerDetailUIItem {
    return BeerDetailUIItem(
        id = this.id,
        name = this.name,
        tagline = this.tagline,
        description = this.description,
        imageUrl = this.imageUrl,
        firstBrewed = this.firstBrewed,
        abv = this.abv?.toString(),
        ibu = this.ibu?.toString(),
        targetFG = this.targetFG?.toString(),
        targetOG = this.targetOG?.toString(),
        ebc = this.ebc?.toString(),
        srm = this.srm?.toString(),
        attenuationLevel = this.attenuationLevel?.toString(),
        volume =
            this.volume?.let {
                "${it.value} ${it.unit}"
            },
        ph = this.ph?.toString(),
        boilVolume =
            this.boilVolume?.let {
                "${it.value} ${it.unit}"
            },
        method =
            this.method?.let {
                val mash =
                    it.mashTemp.map { item ->
                        "Mash - ${item.temp.value}° ${item.temp.unit}"
                    }

                val fermentation =
                    it.fermentation.let { item ->
                        "Fermentation - ${item.temp.value}° ${item.temp.unit}"
                    }

                val twist =
                    it.twist?.let { item ->
                        "Twist - $item"
                    }

                mash.toMutableList().apply {
                    add(fermentation)
                    if (twist != null) {
                        add(twist)
                    }
                }
            },
        ingredients =
            listOf(
                this.ingredients?.malt?.map {
                    "${it.name} - ${it.amount.value} ${it.amount.unit}"
                } ?: emptyList(),
                this.ingredients?.hops?.map {
                    "${it.name} - ${it.amount.value} ${it.amount.unit}"
                } ?: emptyList(),
                this.ingredients?.yeast?.let {
                    listOf("$it")
                } ?: emptyList(),
            ).flatten(),
        foodPairing = this.foodPairing,
        brewersTips = this.brewersTips,
        contributedBy = this.contributedBy,
    )
}
