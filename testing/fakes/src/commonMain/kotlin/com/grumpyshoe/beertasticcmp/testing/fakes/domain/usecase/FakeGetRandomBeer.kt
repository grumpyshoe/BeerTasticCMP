package com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.models.Beer
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetRandomBeer
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiResult
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiSuccess
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.models.fakeBeer

class FakeGetRandomBeer : GetRandomBeer {

    var result: ApiResult<Beer> = ApiSuccess(fakeBeer)

    override suspend fun invoke(): ApiResult<Beer> = result
}
