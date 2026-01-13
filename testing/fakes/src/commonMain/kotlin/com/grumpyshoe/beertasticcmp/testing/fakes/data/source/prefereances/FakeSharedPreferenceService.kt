package com.grumpyshoe.beertasticcmp.testing.fakes.data.source.prefereances

import com.grumpyshoe.beertasticcmp.data.source.preferences.SharedPreferenceService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeSharedPreferenceService : SharedPreferenceService {

    var checkIfFavoriteResult: Boolean = false
    var requestedBeerId: Int? = null
    var requestedFavoriteState: Boolean? = null

    var favoritesResult: List<Int> = emptyList()

    override suspend fun checkIfFavorite(beerId: Int): Boolean {
        requestedBeerId = beerId
        return checkIfFavoriteResult
    }

    override suspend fun setIsBeerFavorite(beerId: Int, isFavorite: Boolean) {
        requestedBeerId = beerId
        requestedFavoriteState = isFavorite
    }

    override val favorites: StateFlow<List<Int>>
        get() = MutableStateFlow<List<Int>>(favoritesResult)
}
