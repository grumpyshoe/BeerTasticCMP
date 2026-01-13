package com.grumpyshoe.beertasticcmp.testing.fakes.data.source.network.beer.datasource

import com.grumpyshoe.beertasticcmp.data.source.network.beer.datasource.BeerRemoteDatasource
import com.grumpyshoe.beertasticcmp.data.source.network.beer.model.BeerDto
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiError
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiResult
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiSuccess
import com.grumpyshoe.beertasticcmp.domain.beer.utils.onError
import com.grumpyshoe.beertasticcmp.domain.beer.utils.onSuccess
import com.grumpyshoe.beertasticcmp.testing.fakes.data.source.network.beer.model.fakeBeerDto

class FakeBeerRemoteDatasource : BeerRemoteDatasource {
    var result: ApiResult<List<BeerDto>> = ApiSuccess(listOf(fakeBeerDto))
    var requestedPage: Int? = null
        private set
    var requestedBeerId: Int? = null
        private set

    override suspend fun getBeers(page: Int): ApiResult<List<BeerDto>> {
        requestedPage = page
        return result
    }

    override suspend fun getBeerById(beerId: Int): ApiResult<BeerDto?> {
        requestedBeerId = beerId
        var parseResult: ApiResult<BeerDto?> = ApiError("Fehler")
        result.onSuccess {
            parseResult = ApiSuccess(it.firstOrNull())
        }.onError {
            parseResult = ApiError(it)
        }
        return parseResult
    }

    override suspend fun getRandomBeer(): ApiResult<BeerDto?> {
        var parseResult: ApiResult<BeerDto?> = ApiError("Fehler")
        result.onSuccess {
            parseResult = ApiSuccess(it.firstOrNull())
        }.onError {
            parseResult = ApiError(it)
        }
        return parseResult
    }
}
