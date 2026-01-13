package com.grumpyshoe.beertasticcmp.domain.beer.di

import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetBeerById
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetBeerByIdImpl
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetBeers
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetBeersImpl
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetFavorites
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetFavoritesImpl
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetRandomBeer
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.GetRandomBeerImpl
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.IsBeerFavorite
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.IsBeerFavoriteImpl
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.SetIsBeerFavorite
import com.grumpyshoe.beertasticcmp.domain.beer.usecase.SetIsBeerFavoriteImpl
import org.koin.dsl.module

val BeerDomainModule =
    module {
        factory<GetBeers> { GetBeersImpl(get()) }
        factory<GetBeerById> { GetBeerByIdImpl(get()) }
        factory<IsBeerFavorite> { IsBeerFavoriteImpl(get()) }
        factory<SetIsBeerFavorite> { SetIsBeerFavoriteImpl(get()) }
        factory<GetFavorites> { GetFavoritesImpl(get()) }
        factory<GetRandomBeer> { GetRandomBeerImpl(get()) }
    }
