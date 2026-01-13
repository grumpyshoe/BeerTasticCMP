package com.grumpyshoe.beertasticcmp.presentation.features.details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.grumpyshoe.beertasticcmp.presentation.common.resources.AppTheme
import com.grumpyshoe.beertasticcmp.presentation.features.details.ui.targets.Details
import com.grumpyshoe.beertasticcmp.presentation.features.details.ui.targets.Error
import com.grumpyshoe.beertasticcmp.presentation.features.details.ui.targets.Loading
import com.grumpyshoe.beertasticcmp.presentation.features.details.ui.uimodel.BeerDetailUIItem
import com.grumpyshoe.beertasticcmp.presentation.features.details.ui.state.DetailsViewState
import com.grumpyshoe.beertasticcmp.presentation.features.details.viewmodel.DetailsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsRoot(
    beerId: Int,
    viewModel: DetailsViewModel = koinViewModel(
        key = beerId.toString(),
        parameters = { parametersOf(beerId) }),
    navigateBack: () -> Unit,
) {
    val beerDetailsDataState by viewModel.viewState.collectAsStateWithLifecycle()

    DetailsScreen(
        state = beerDetailsDataState,
        onAction = viewModel::onAction,
        navigateBack = navigateBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreen(
    state: DetailsViewState,
    onAction: (DetailsAction) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold(

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "More about...",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                actions = {
                    val icon = if (state.beerDetails != null && state.isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    }
                    IconButton(onClick = { onAction(DetailsAction.ToggleFavoriteState) }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                state.isLoading -> Loading()
                state.hasError -> Error()
                state.beerDetails != null -> Details(beerDetails = state.beerDetails)
            }
        }
    }
}

@Preview
@Composable
private fun BerDetailsScreenLoadingPreview() {
    AppTheme {
        DetailsScreen(
            state = DetailsViewState(isLoading = true),
            navigateBack = {},
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun BerDetailsScreenErrorPreview() {
    AppTheme {
        DetailsScreen(
            state = DetailsViewState(hasError = true),
            navigateBack = {},
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun BerDetailsScreenBeerOverviewPreview() {
    AppTheme {
        DetailsScreen(
            state = DetailsViewState(
                isFavorite = false,
                beerDetails = BeerDetailUIItem(
                    id = 0,
                    name = "item_0",
                    tagline = "tag1, tag2",
                    description = "description 123",
                    imageUrl = null,
                    firstBrewed = "2012-04-12",
                    abv = "6.0",
                    ibu = "60.0",
                    targetFG = "1010.0",
                    targetOG = "1056.0",
                    ebc = "17.0",
                    srm = "8.5",
                    ph = "4.4",
                    attenuationLevel = "82.14",
                    volume = "20 liters",
                    boilVolume = "25 liters",
                    method = listOf(
                        "Mash - 65°C - 75min.",
                        "Fermentation - 19°C - ",
                    ),
                    ingredients = listOf(
                        "malt - Extra Pale - 5.3Kg",
                        "hops - Ahtanum - 17.5g",
                        "hops - Chinook - 15g - start - (bitter)",
                    ),
                    foodPairing = listOf(
                        "Spicy carne asada with a pico de gallo sauce",
                        "Shredded chicken tacos with a mango chilli lime salsa",
                        "Cheesecake with a passion fruit swirl sauce",
                    ),
                    brewersTips = "brewer tips",
                    contributedBy = "Sam Mason <samjbmason>",
                ),
            ),
            navigateBack = {},
            onAction = {},
        )
    }
}
