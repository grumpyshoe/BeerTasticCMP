package com.grumpyshoe.beertasticcmp.presentation.features.home.viewmodel

import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiSuccess
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.HomeAction
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.state.HomeViewState
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.models.fakeBeer
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.models.getFakeBeer
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase.FakeGetBeers
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase.FakeGetFavorites
import com.grumpyshoe.beertasticcmp.testing.fakes.domain.usecase.FakeGetRandomBeer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var getBeers: FakeGetBeers
    private lateinit var getFavorites: FakeGetFavorites
    private lateinit var getRandomBeer: FakeGetRandomBeer
    private lateinit var testDispatcher: TestDispatcher

    private lateinit var sut: HomeViewModel

    @BeforeTest
    fun setup() {
        getBeers = FakeGetBeers()
        getFavorites = FakeGetFavorites()
        getRandomBeer = FakeGetRandomBeer()

        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    private fun initViewModel() {
        sut = HomeViewModel(
            getBeers = getBeers,
            getFavorites = getFavorites,
            getRandomBeer = getRandomBeer,
            ioDispatcher = testDispatcher,
        )
    }

    @Test
    fun viewState___on_data_available___has_correct_state() = runTest {
        // define test data
        getBeers.result = ApiSuccess((0..3).map { getFakeBeer(it) })

        // init viewModel
        initViewModel()

        // collect data
        val results = mutableListOf<HomeViewState>()
        backgroundScope.launch(testDispatcher) {
            sut.viewState.collect { results.add(it) }
        }
        advanceUntilIdle()

        // check assertions
        assertTrue(results.last().beerList.isNotEmpty())
    }

    @Test
    fun beerList___on_data_available___has_correct_data_length() = runTest {
        // define test data
        getBeers.result = ApiSuccess((0..2).map { getFakeBeer(it) })

        // init viewModel
        initViewModel()

        // collect data
        val results = mutableListOf<HomeViewState>()
        backgroundScope.launch(testDispatcher) {
            sut.viewState.collect { results.add(it) }
        }
        advanceUntilIdle()

        // check assertions
        assertEquals(3, results.last().beerList.size)
    }

    @Test
    fun beerList___on_data_available___mapped_data_correctly() = runTest {
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

        // collect data
        val results = mutableListOf<HomeViewState>()
        backgroundScope.launch(testDispatcher) {
            sut.viewState.collect { results.add(it) }
        }
        advanceUntilIdle()

        // check assertions
        val actual = results.last().beerList.first()
        assertEquals(99, actual?.id)
        assertEquals("beer_123", actual?.name)
        assertEquals("tag 1", actual?.tagline)
        assertEquals("dummy description", actual?.shortDescription)
        assertEquals("dummy image url", actual?.imageUrl)
    }

    @Test
    fun beerList___on_data_available___shorten_description_to_50_chars_and_ellipsis() = runTest {
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

        // collect data
        val results = mutableListOf<HomeViewState>()
        backgroundScope.launch(testDispatcher) {
            sut.viewState.collect { results.add(it) }
        }
        advanceUntilIdle()

        // check assertions
        val actual = results.last().beerList.take(1).firstOrNull()
        assertEquals(53, actual?.shortDescription?.length)
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
        sut.onAction(HomeAction.LoadMore)

        // check assertions
        assertEquals(2, getBeers.requestedPage)
    }

    @Test
    fun beerList___on_favorites_available___doesnt_contain_duplicates() = runTest {
        // define test data
        getBeers.result = ApiSuccess((0..2).map { getFakeBeer(it) })
        getFavorites.result = listOf(fakeBeer.id)

        // init viewModel
        initViewModel()

        // collect data
        val results = mutableListOf<HomeViewState>()
        backgroundScope.launch(testDispatcher) {
            sut.viewState.collect { results.add(it) }
        }
        advanceUntilIdle()

        // check assertions
        val actual = results.last().beerList
        assertEquals(2, actual?.size)
        assertEquals(actual?.map { it.id }?.contains(fakeBeer.id), false)
    }

    @Test
    fun favorites___on_favorites_available___contains_correct_data() = runTest {
        // define test data
        getFavorites.result = listOf(fakeBeer.id)

        // init viewModel
        initViewModel()

        // collect data
        val results = mutableListOf<HomeViewState>()
        backgroundScope.launch(testDispatcher) {
            sut.viewState.collect { results.add(it) }
        }
        advanceUntilIdle()

        // check assertions
        val actual = results.last().favorites
        assertEquals(1, actual?.size)
        assertEquals(actual?.map { it.id }?.contains(fakeBeer.id), true)
    }

    @Test
    fun randomBeer___on_init___is_null() = runTest {
        // init viewModel
        initViewModel()

        // collect data
        val results = mutableListOf<HomeViewState>()
        backgroundScope.launch(testDispatcher) {
            sut.viewState.collect { results.add(it) }
        }
        advanceUntilIdle()

        // check assertions
        val actual = results.last().randomBeer
        assertNull(actual)
    }

    @Test
    fun randomBeer___on_request_beer___contains_correct_data() = runTest {
        // define test data
        getRandomBeer.result = ApiSuccess(fakeBeer)

        // init viewModel
        initViewModel()

        // collect data
        val results = mutableListOf<HomeViewState>()
        backgroundScope.launch(testDispatcher) {
            sut.viewState.collect { results.add(it) }
        }

        // trigger action
        sut.onAction(HomeAction.ShowRandomBeer)
        advanceUntilIdle()

        // check assertions
        val actual = results.last().randomBeer
        assertEquals(fakeBeer.id, actual!!.id)
    }

    @Test
    fun randomBeer___on_delete_random_selection___data_is_removed() = runTest {
        // define test data
        getRandomBeer.result = ApiSuccess(fakeBeer)

        // init viewModel
        initViewModel()

        // collect data
        val results = mutableListOf<HomeViewState>()
        backgroundScope.launch(testDispatcher) {
            sut.viewState.collect { results.add(it) }
        }

        // trigger action
        sut.onAction(HomeAction.HideRandomBeer)
        advanceUntilIdle()

        // check assertions
        val actual = results.last().randomBeer
        assertNull(actual)
    }
}
