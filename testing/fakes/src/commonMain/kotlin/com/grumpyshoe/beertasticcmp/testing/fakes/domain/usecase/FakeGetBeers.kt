package com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.models.Beer
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetBeers
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiResult
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiSuccess
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.models.fakeBeer

class FakeGetBeers : GetBeers {

    var result: ApiResult<List<Beer>> = ApiSuccess(listOf(fakeBeer))
    var requestedPage: Int? = null
        private set

    override suspend fun invoke(page: Int): ApiResult<List<Beer>> {
        requestedPage = page
        return result
    }
}
