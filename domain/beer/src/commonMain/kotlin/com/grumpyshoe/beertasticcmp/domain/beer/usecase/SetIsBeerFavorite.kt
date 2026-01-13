package com.grumpyshoe.beertasticcmp.domain.beer.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.repository.BeerRepository

interface SetIsBeerFavorite {
    suspend operator fun invoke(beerId: Int, isFavorite: Boolean)
}

class SetIsBeerFavoriteImpl(private val beerRepository: BeerRepository) : SetIsBeerFavorite {
    override suspend fun invoke(beerId: Int, isFavorite: Boolean) {
        beerRepository.setIsBeerFavorite(
            beerId = beerId,
            isFavorite = isFavorite,
        )
    }
}
