package com.grumpyshoe.beertasticcmp.presentation.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grumpyshoe.beertasticcmp.domain.beer.models.Beer
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetBeers
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetFavorites
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetRandomBeer
import com.grumpyshoe.beertasticcmp.domain.beer.utils.onError
import com.grumpyshoe.beertasticcmp.domain.beer.utils.onSuccess
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.HomeAction
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.HomeEvent
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.state.HomeViewState
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.uimodel.BeerUIItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getBeers: GetBeers,
    private val getFavorites: GetFavorites,
    private val getRandomBeer: GetRandomBeer,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val beerList = MutableStateFlow<List<Beer>>(emptyList())
    private val randomBeer = MutableStateFlow<BeerUIItem?>(null)
    private val errorOccurred = MutableStateFlow<Boolean>(false)

    val viewState: StateFlow<HomeViewState> = combine(
        beerList,
        randomBeer,
        getFavorites(),
        errorOccurred
    ) { beerList, randomBeer, favorites, errorOccurred ->

        val favoriteBeers = beerList.filter { favorites.contains(it.id) }
        val otherBeers = beerList.toMutableList().apply {
            removeAll(favoriteBeers)
        }

        HomeViewState(
            randomBeer = randomBeer,
            favorites = favoriteBeers.map { it.mapToUIModel() },
            beerList = otherBeers.map { it.mapToUIModel() },
            hasError = errorOccurred
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeViewState()
    )

    private val _events = MutableSharedFlow<HomeEvent?>()
    val events = _events.asSharedFlow()

    private var page = 1

    init {
        loadBeerData()
    }

    private fun loadBeerData() {
        viewModelScope.launch(ioDispatcher) {

            errorOccurred.update { false }

            getBeers(page)
                .onSuccess { loadedBeers ->

                    val currentItems = beerList.value
                    val allItem = currentItems + loadedBeers
                    beerList.update {
                        it.toMutableList().apply {
                            clear()
                            addAll(allItem)
                        }
                    }
                }.onError {
                    errorOccurred.update { false }
                }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.HideRandomBeer -> {
                randomBeer.tryEmit(null)
            }

            is HomeAction.LoadMore -> {
                page++
                loadBeerData()
            }

            is HomeAction.ShowDetails -> {
                viewModelScope.launch(ioDispatcher) {
                    _events.emit(HomeEvent.NavigateToDetails(action.beerId))
                }
            }

            is HomeAction.ShowRandomBeer -> {
                viewModelScope.launch(ioDispatcher) {
                    getRandomBeer().onSuccess { beer ->
                        randomBeer.emit(beer.mapToUIModel())
                    }
                }
            }
        }
    }
}

private fun Beer.mapToUIModel(): BeerUIItem = BeerUIItem(
    id = id,
    name = name,
    shortDescription = description.shortenTo50Chars(),
    imageUrl = imageUrl,
    tagline = tagline,
)

private fun String?.shortenTo50Chars(): String = if (this != null && this.length > 50) {
    "${this.substring(0, 50)}..."
} else {
    this
} ?: ""
