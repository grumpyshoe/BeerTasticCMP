package com.grumpyshoe.beertasticcmp.domain.beer.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.models.Beer
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiResult
import com.grumpyshoe.beertasticcmp.domain.beer.repository.BeerRepository

interface GetRandomBeer {
    suspend operator fun invoke(): ApiResult<Beer>
}

class GetRandomBeerImpl(private val beerRepository: BeerRepository) : GetRandomBeer {
    override suspend fun invoke(): ApiResult<Beer> = beerRepository.getRandomBeer()
}