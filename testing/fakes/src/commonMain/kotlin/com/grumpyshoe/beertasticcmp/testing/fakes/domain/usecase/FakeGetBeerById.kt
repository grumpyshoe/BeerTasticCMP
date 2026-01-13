package com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.models.Beer
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetBeerById
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiResult
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiSuccess
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.models.fakeBeer

class FakeGetBeerById : GetBeerById {

    var result: ApiResult<Beer> = ApiSuccess(fakeBeer)
    var requestedBeerId: Int? = null
        private set

    override suspend fun invoke(beerId: Int): ApiResult<Beer> {
        requestedBeerId = beerId
        return result
    }
}
