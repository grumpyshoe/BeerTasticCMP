package com.grumpyshoe.beertasticcmp.data.repository

import com.grumpyshoe.beertasticcmp.domain.beer.repository.BeerRepository
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiError
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiSuccess
import com.grumpyshoe.beertasticcmp.testing.fakes.data.source.network.beer.datasource.FakeBeerRemoteDatasource
import com.grumpyshoe.beertasticcmp.testing.fakes.data.source.network.beer.model.getFakeBeerDto
import com.grumpyshoe.beertasticcmp.testing.fakes.data.source.prefereances.FakeSharedPreferenceService
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BeerRepositoryImplTest {

    private lateinit var beerRemoteDatasource: FakeBeerRemoteDatasource
    private lateinit var sharedPreferenceService: FakeSharedPreferenceService

    private lateinit var sut: BeerRepository

    @BeforeTest
    fun setup() {
        beerRemoteDatasource = FakeBeerRemoteDatasource()
        sharedPreferenceService = FakeSharedPreferenceService()
    }

    private fun initViewModel() {
        sut = BeerRepositoryImpl(
            beerRemoteDatasource = beerRemoteDatasource,
            sharedPreferenceService = sharedPreferenceService,
        )
    }

    @Test
    fun getBeerById___on_ApiSuccess___data_are_mapped_correct() {
        // define test data
        val beerDto = getFakeBeerDto(0)
        beerRemoteDatasource.result = ApiSuccess(listOf(beerDto))

        // init viewModel
        initViewModel()

        // trigger action
        val actual = runBlocking {
            sut.getBeerById(0).let {
                (it as ApiSuccess).data
            }
        }

        // check assertions
        assertEquals(beerDto.id, actual.id)
        assertEquals(beerDto.name, actual.name)
        assertEquals(beerDto.tagline, actual.tagline)
        assertEquals(beerDto.firstBrewed, actual.firstBrewed)
        assertEquals(beerDto.description, actual.description)
        assertEquals("/images/${beerDto.imageId}", actual.imageUrl)
        assertEquals(beerDto.abv, actual.abv)
        assertEquals(beerDto.ibu, actual.ibu)
        assertEquals(beerDto.targetFg, actual.targetFG)
        assertEquals(beerDto.targetOg, actual.targetOG)
        assertEquals(beerDto.ebc, actual.ebc)
        assertEquals(beerDto.srm, actual.srm)
        assertEquals(beerDto.ph, actual.ph)
        assertEquals(beerDto.attenuationLevel, actual.attenuationLevel)
        assertEquals(beerDto.volume?.value, actual.volume!!.value)
        assertEquals(beerDto.volume?.unit, actual.volume!!.unit)
        assertEquals(beerDto.boilVolume?.value, actual.boilVolume!!.value)
        assertEquals(beerDto.boilVolume?.unit, actual.boilVolume!!.unit)
        assertEquals(beerDto.method?.mashTemp?.size, actual.method!!.mashTemp.size)
        assertEquals(
            beerDto.ingredients?.hops?.size,
            actual.ingredients!!.hops!!.size
        )
        assertEquals(
            beerDto.ingredients?.malt?.size,
            actual.ingredients!!.malt!!.size
        )
        assertEquals(beerDto.ingredients?.yeast, actual.ingredients!!.yeast)
        assertEquals(
            beerDto.foodPairing?.joinToString(),
            actual.foodPairing!!.joinToString()
        )
        assertEquals(beerDto.brewersTips, actual.brewersTips)
        assertEquals(beerDto.contributedBy, actual.contributedBy)
    }

    @Test
    fun getBeerById___on_ApiError___data_are_mapped_correct() {
        // define test data
        val errorMsg = "DummyError"
        beerRemoteDatasource.result = ApiError(errorMsg)

        // init viewModel
        initViewModel()

        // trigger action
        val actual = runBlocking {
            sut.getBeerById(0).let {
                (it as ApiError).msg
            }
        }

        // check assertions
        assertEquals(errorMsg, actual)
    }

    @Test
    fun getBeers___on_ApiSuccess___data_are_mapped_correct() {
        // define test data
        val beerDtoList = listOf(
            getFakeBeerDto(0),
            getFakeBeerDto(15),
        )
        beerRemoteDatasource.result = ApiSuccess(beerDtoList)

        // init viewModel
        initViewModel()

        // trigger action
        val actual = runBlocking {
            sut.getBeers(0).let {
                (it as ApiSuccess).data
            }
        }

        // check assertions
        assertEquals(2, actual.size)
        assertEquals(0, actual[0].id)
        assertEquals(15, actual[1].id)
    }

    @Test
    fun getBeers___on_ApiError___data_are_mapped_correct() {
        // define test data
        val errorMsg = "DummyError"
        beerRemoteDatasource.result = ApiError(errorMsg)

        // init viewModel
        initViewModel()

        // trigger action
        val actual = runBlocking {
            sut.getBeers(0).let {
                (it as ApiError).msg
            }
        }

        // check assertions
        assertEquals(errorMsg, actual)
    }

    @Test
    fun getFavorites___on_data_available___mapped_data_correct() {
        // define test data
        val beerDtoList = listOf(5, 8, 9)
        sharedPreferenceService.favoritesResult = beerDtoList

        // init viewModel
        initViewModel()

        // trigger action
        val actual = runBlocking { sut.getFavorites().take(1).lastOrNull() }

        // check assertions
        assertEquals(3, actual?.size)
        assertEquals(5, actual?.get(0))
        assertEquals(8, actual?.get(1))
        assertEquals(9, actual?.get(2))
    }
}
