package com.grumpyshoe.beertasticcmp.domain.beer.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.models.Beer
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiResult
import com.grumpyshoe.beertasticcmp.domain.beer.repository.BeerRepository

interface GetBeerById {
    suspend operator fun invoke(beerId: Int): ApiResult<Beer>
}

class GetBeerByIdImpl(private val beerRepository: BeerRepository) : GetBeerById {
    override suspend fun invoke(beerId: Int): ApiResult<Beer> = beerRepository.getBeerById(beerId = beerId)
}