package com.grumpyshoe.beertasticcmp.presentation.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.grumpyshoe.beertasticcmp.presentation.common.resources.AppTheme
import com.grumpyshoe.beertasticcmp.presentation.common.resources.Res
import com.grumpyshoe.beertasticcmp.presentation.common.resources.home_title
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.state.HomeViewState
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.uimodel.BeerUIItem
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.targets.Dashboard
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.targets.Error
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.targets.Loading
import com.grumpyshoe.beertasticcmp.presentation.features.home.viewmodel.HomeViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoot(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToDetails: (Int) -> Unit,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle(HomeViewState(isLoading = true))
    val events by viewModel.events.collectAsStateWithLifecycle(null)

    LaunchedEffect(events) {
        when (val currentEvent = events) {
            null -> return@LaunchedEffect
            is HomeEvent.NavigateToDetails -> navigateToDetails(currentEvent.beerId)
        }
    }

    HomeScreen(
        homeViewState = viewState,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    homeViewState: HomeViewState,
    onAction: (HomeAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.home_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                homeViewState.isLoading -> Loading()
                homeViewState.hasError -> Error()
                homeViewState.beerList.isNotEmpty() -> Dashboard(
                    favorites = homeViewState.favorites,
                    beerList = homeViewState.beerList,
                    randomBeer = homeViewState.randomBeer,
                    onAction = onAction,
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    AppTheme {
        HomeScreen(
            homeViewState = HomeViewState(isLoading = true),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun HomeScreenErrorPreview() {
    AppTheme {
        HomeScreen(
            homeViewState = HomeViewState(hasError = true),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun HomeScreenBeerOverviewPreview() {
    AppTheme {
        HomeScreen(
            homeViewState = HomeViewState(
                beerList = (4..12).map {
                    BeerUIItem(
                        id = it,
                        name = "item_$it",
                        shortDescription = "description 123",
                        imageUrl = null,
                        tagline = "Tag1, Tag2",
                    )
                },
                favorites = listOf(
                    BeerUIItem(
                        id = 3,
                        name = "item_3",
                        shortDescription = "description 123",
                        imageUrl = null,
                        tagline = "Tag1, Tag2",
                    ),
                )
            ),
            onAction = {}
        )
    }
}
