package com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.usecase.IsBeerFavorite

class FakeIsBeerFavorite : IsBeerFavorite {

    var result: Boolean = false
    var requestedBeerId: Int? = null
        private set

    override suspend fun invoke(beerId: Int): Boolean {
        requestedBeerId = beerId
        return result
    }
}
