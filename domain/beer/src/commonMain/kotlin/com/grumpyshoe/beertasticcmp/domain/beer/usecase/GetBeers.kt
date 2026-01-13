package com.grumpyshoe.beertasticcmp.domain.beer.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.models.Beer
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiResult
import com.grumpyshoe.beertasticcmp.domain.beer.repository.BeerRepository

interface GetBeers {
    suspend operator fun invoke(page: Int): ApiResult<List<Beer>>
}

class GetBeersImpl(private val beerRepository: BeerRepository) : GetBeers {
    override suspend fun invoke(page: Int): ApiResult<List<Beer>> = beerRepository.getBeers(page = page)
}
