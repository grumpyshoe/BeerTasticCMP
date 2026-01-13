package com.grumpyshoe.beertasticcmp.presentation.features.home.viewmodel

import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiError
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiSuccess
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.state.HomeViewState
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.models.fakeBeer
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.models.getFakeBeer
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase.FakeGetBeerById
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase.FakeGetBeers
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase.FakeGetFavorites
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase.FakeGetRandomBeer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var getBeers: FakeGetBeers
    private lateinit var getBeerById: FakeGetBeerById
    private lateinit var getFavorites: FakeGetFavorites
    private lateinit var getRandomBeer: FakeGetRandomBeer
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var sut: HomeViewModel

    @BeforeTest
    fun setup() {
        getBeers = FakeGetBeers()
        getBeerById = FakeGetBeerById()
        getFavorites = FakeGetFavorites()
        getRandomBeer = FakeGetRandomBeer()
    }

    private fun initViewModel() {
        sut = HomeViewModel(
            getBeers = getBeers,
            getBeerById = getBeerById,
            getFavorites = getFavorites,
            getRandomBeer = getRandomBeer,
            ioDispatcher = testDispatcher,
        )
    }

    @Test
    fun viewState___on_data_available___has_correct_state() {
        // define test data
        getBeers.result = ApiSuccess((0..3).map { getFakeBeer(it) })

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.viewState.take(1).lastOrNull() }
        assertTrue(actual is HomeViewState.DataLoaded)
    }

    @Test
    fun beerList___on_data_available___has_correct_data_length() {
        // define test data
        getBeers.result = ApiSuccess((0..2).map { getFakeBeer(it) })

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.beerList.take(1).lastOrNull() }
        assertEquals(3, actual?.size)
    }

    @Test
    fun beerList___on_data_available___mapped_data_correctly() {
        // define test data
        getBeers.result = ApiSuccess(
            listOf(
                fakeBeer.copy(
                    id = 99,
                    name = "beer_123",
                    tagline = "tag 1",
                    firstBrewed = "2012-05-14",
                    description = "dummy description",
                    imageUrl = "dummy image url",
                ),
            ),
        )

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.beerList.take(1).lastOrNull()?.firstOrNull() }
        assertEquals(99, actual?.id)
        assertEquals("beer_123", actual?.name)
        assertEquals("tag 1", actual?.tagline)
        assertEquals("dummy description", actual?.shortDescription)
        assertEquals("dummy image url", actual?.imageUrl)
    }

    @Test
    fun beerList___on_data_available___shorten_description_to_50_chars_and_ellipsis() {
        // define test data
        getBeers.result = ApiSuccess(
            listOf(
                fakeBeer.copy(
                    description = (0..100).joinToString(separator = "") { "a" },
                ),
            ),
        )

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.beerList.take(1).lastOrNull()?.firstOrNull() }
        assertEquals(53, actual?.shortDescription?.length)
    }

    @Test
    fun viewState___on_error___is_set_correctly() {
        // define test data
        getBeers.result = ApiError("DummyError")

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.viewState.take(1).lastOrNull() }
        assertTrue(actual is HomeViewState.Error)
    }

    @Test
    fun request_page___on_init___is_1() {
        // define test data
        getBeers.result = ApiSuccess(listOf(fakeBeer))

        // init viewModel
        initViewModel()

        // check assertions
        assertEquals(1, getBeers.requestedPage)
    }

    @Test
    fun requested_page___on_loadMoreData___is_increased_by_1() {
        // define test data
        getBeers.result = ApiSuccess(listOf(fakeBeer))

        // init viewModel
        initViewModel()

        // trigger action
        sut.loadMoreData()

        // check assertions
        assertEquals(2, getBeers.requestedPage)
    }

    @Test
    fun beerList___on_favorites_available___doesnt_contain_duplicates() {
        // define test data
        getBeers.result = ApiSuccess((0..2).map { getFakeBeer(it) })
        getFavorites.result = listOf(fakeBeer.id)

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.beerList.take(1).lastOrNull() }
        assertEquals(2, actual?.size)
        assertEquals(actual?.map { it.id }?.contains(fakeBeer.id), false)
    }

    @Test
    fun favorites___on_favorites_available___contains_correct_data() {
        // define test data
        getFavorites.result = listOf(fakeBeer.id)

        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.favorites.take(1).lastOrNull() }
        assertEquals(1, actual?.size)
        assertEquals(actual?.map { it.id }?.contains(fakeBeer.id), true)
    }

    @Test
    fun randomBeer___on_init___is_null() {
        // init viewModel
        initViewModel()

        // check assertions
        val actual = runBlocking { sut.randomBeer.take(1).lastOrNull() }
        assertNull(actual)
    }

    @Test
    fun randomBeer___on_request_beer___contains_correct_data() {
        // define test data
        getRandomBeer.result = ApiSuccess(fakeBeer)

        // init viewModel
        initViewModel()

        // trigger action
        sut.showRandomBeer()

        // check assertions
        val actual = runBlocking { sut.randomBeer.take(1).lastOrNull() }
        assertEquals(fakeBeer.id, actual!!.id)
    }

    @Test
    fun randomBeer___on_delete_random_selection___data_is_removed() {
        // define test data
        getRandomBeer.result = ApiSuccess(fakeBeer)

        // init viewModel
        initViewModel()

        // trigger action
        sut.hideRandomBeer()

        // check assertions
        val actual = runBlocking { sut.randomBeer.take(1).lastOrNull() }
        assertNull(actual)
    }
}
