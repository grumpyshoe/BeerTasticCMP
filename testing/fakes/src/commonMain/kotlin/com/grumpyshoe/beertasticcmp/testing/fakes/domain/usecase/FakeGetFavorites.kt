package com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase

import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetFavorites
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetFavorites : GetFavorites {

    var result: List<Int> = listOf()

    override fun invoke(): Flow<List<Int>> = flow { emit(result) }
}
