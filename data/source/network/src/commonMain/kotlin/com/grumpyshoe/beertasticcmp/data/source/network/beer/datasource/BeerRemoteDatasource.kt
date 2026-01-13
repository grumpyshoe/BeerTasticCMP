package com.grumpyshoe.beertasticcmp.data.source.network.beer.datasource

import com.grumpyshoe.beertasticcmp.data.source.network.beer.api.PunkAPI
import com.grumpyshoe.beertasticcmp.data.source.network.beer.model.BeerDto
import com.grumpyshoe.beertasticcmp.data.source.network.handleApi
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiResult

interface BeerRemoteDatasource {
    suspend fun getBeers(page: Int): ApiResult<List<BeerDto>>
    suspend fun getBeerById(beerId: Int): ApiResult<BeerDto?>
    suspend fun getRandomBeer(): ApiResult<BeerDto?>
}

class BeerRemoteDatasourceImpl(private val api: PunkAPI) : BeerRemoteDatasource {
    override suspend fun getBeers(page: Int): ApiResult<List<BeerDto>> =
        handleApi { api.getBeers(page = page) }

    override suspend fun getBeerById(beerId: Int): ApiResult<BeerDto> = handleApi {
        api.getBeerById(beerId)
    }

    override suspend fun getRandomBeer(): ApiResult<BeerDto?> = handleApi { api.getRandomBeer() }
}