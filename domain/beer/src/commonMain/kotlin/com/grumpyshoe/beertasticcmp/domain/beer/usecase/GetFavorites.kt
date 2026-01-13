package com.grumpyshoe.beertasticcmp.domain.beer.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.repository.BeerRepository
import kotlinx.coroutines.flow.Flow

interface GetFavorites {
    operator fun invoke(): Flow<List<Int>>
}

class GetFavoritesImpl(private val beerRepository: BeerRepository) : GetFavorites {
    override fun invoke(): Flow<List<Int>> = beerRepository.getFavorites()
}
