package com.grumpyshoe.beertasticcmp.presentation.features.details.viewmodel

import com.grumpyshoe.beertasticcmp.domain.beer.usecase.SetIsBeerFavorite
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiError
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiSuccess
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.models.fakeBeer
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase.FakeGetBeerById
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase.FakeIsBeerFavorite
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase.FakeSetIsBeerFavorite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private lateinit var getBeerById: FakeGetBeerById
    private lateinit var isBeerFavorite: FakeIsBeerFavorite
    private lateinit var setIsBeerFavorite: SetIsBeerFavorite
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var sut: DetailsViewModel

    @BeforeTest
    fun setup() {
        getBeerById = FakeGetBeerById()
        isBeerFavorite = FakeIsBeerFavorite()
        setIsBeerFavorite = FakeSetIsBeerFavorite()
    }

    private fun initViewModel(beerId: Int = 1) {
        sut = DetailsViewModel(
            beerId = beerId,
            getBeerById = getBeerById,
            ioDispatcher = testDispatcher,
            isBeerFavorite = isBeerFavorite,
            setIsBeerFavorite = setIsBeerFavorite,
        )
    }

    @Test
    fun on_init___with_beer_id___correct_id_is_requested() {
        // define test data
        getBeerById.result = ApiSuccess(fakeBeer)

        // init viewModel
        initViewModel(beerId = 99)

        // check assertions
        assertEquals(99, getBeerById.requestedBeerId)
    }

    @Test
    fun beerDetailsDataState___on_data_available___is_mapped_correctly() {
        // define test data
        getBeerById.result = ApiSuccess(fakeBeer)

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.viewState.take(1).last() }
        assertTrue(actual.beerDetails != null)
    }

    @Test
    fun beerDetailsDataState___on_error___is_mapped_correctly() {
        // define test data
        getBeerById.result = ApiError("DummyError")

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.viewState.take(1).last() }
        assertTrue(actual.hasError)
    }

    @Test
    fun isFavorite___on_not_favorite_beer___is_false() {
        // define test data
        isBeerFavorite.result = false

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.viewState.take(1).last() }
        assertFalse(actual.isFavorite)
    }

    @Test
    fun isFavorite___on_favorite___is_true() {
        // define test data
        isBeerFavorite.result = true

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.viewState.take(1).last() }
        assertTrue(actual.isFavorite)
    }
}
