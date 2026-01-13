package com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.usecase.SetIsBeerFavorite

class FakeSetIsBeerFavorite : SetIsBeerFavorite {

    var requestedBeerId: Int? = null
        private set

    override suspend fun invoke(beerId: Int, isFavorite: Boolean) {
        requestedBeerId = beerId
    }
}
