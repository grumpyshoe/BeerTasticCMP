package com.grumpyshoe.beertasticcmp.domain.beer.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.repository.BeerRepository

interface IsBeerFavorite {
    suspend operator fun invoke(beerId: Int): Boolean
}

class IsBeerFavoriteImpl(private val beerRepository: BeerRepository) : IsBeerFavorite {
    override suspend fun invoke(beerId: Int): Boolean = beerRepository.checkIfFavorite(beerId = beerId)
}
