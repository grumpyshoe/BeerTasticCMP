package com.grumpyshoe.beertasticcmp.presentation.features.home.ui.targets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.grumpyshoe.beertasticcmp.presentation.common.resources.AppTheme
import com.grumpyshoe.beertasticcmp.presentation.common.resources.Res
import com.grumpyshoe.beertasticcmp.presentation.common.resources.home_section_all_beers
import com.grumpyshoe.beertasticcmp.presentation.common.resources.home_section_favorites
import com.grumpyshoe.beertasticcmp.presentation.common.resources.questionmark
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.HomeAction
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.components.RandomBeer
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.uimodel.BeerUIItem
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Dashboard(
    favorites: List<BeerUIItem>?,
    beerList: List<BeerUIItem>,
    randomBeer: BeerUIItem?,
    onAction: (HomeAction) -> Unit,
) {
    var loading by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { index ->
                if (!loading && index != null && index >= beerList.size - 5) {
                    onAction(HomeAction.LoadMore)
                    loading = true
                }
            }
    }
    LaunchedEffect(beerList) {
        loading = false
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            RandomBeer(
                randomBeer = randomBeer,
                onAction = onAction
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!favorites.isNullOrEmpty()) {
            item {
                SectionHeadline(Res.string.home_section_favorites)
            }

            items(favorites, key = { it.id }) { beerUIItem ->

                BeerUIItemView(
                    beerItem = beerUIItem,
                    showDetails = { onAction(HomeAction.ShowDetails(it)) },
                    cardBackground = MaterialTheme.colorScheme.primary.copy(alpha = .6f),
                    borderStrokeColor = MaterialTheme.colorScheme.primary,
                )
            }
        }

        if (beerList.isNotEmpty()) {
            item {
                SectionHeadline(Res.string.home_section_all_beers)

                Spacer(modifier = Modifier.height(8.dp))
            }

            items(items = beerList, key = { it.id }) { beerUIItem ->

                BeerUIItemView(
                    beerItem = beerUIItem,
                    showDetails = { onAction(HomeAction.ShowDetails(it)) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BeerUIItemView(
    beerItem: BeerUIItem,
    showDetails: (Int) -> Unit,
    cardBackground: Color = MaterialTheme.colorScheme.surfaceContainer,
    borderStrokeColor: Color = MaterialTheme.colorScheme.surface,
) {
    Box(modifier = Modifier.padding(bottom = 16.dp)) {
        Card(
            border = BorderStroke(2.dp, color = borderStrokeColor),
            colors = CardDefaults.cardColors(
                containerColor = cardBackground,
            ),
        ) {
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent,

                    ),
                modifier = Modifier
                    .clickable {
                        showDetails(beerItem.id)
                    }
                    .padding(vertical = 8.dp),
                leadingContent = {
                    Box(
                        modifier = Modifier.size(56.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (beerItem.imageUrl == null) {
                            Box(
                                modifier = Modifier
                                    .size(96.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(alpha = .3f),
                                        shape = CircleShape,
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Image(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(Res.drawable.questionmark),
                                    contentDescription = null,
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                    .background(
                                        MaterialTheme.colorScheme.surfaceContainer.copy(
                                            alpha = .6f
                                        )
                                    )
                                    .padding(horizontal = 8.dp),
                            ) {
                                AsyncImage(
                                    model = beerItem.imageUrl,
                                    placeholder = painterResource(Res.drawable.questionmark),
                                    contentDescription = beerItem.name,
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier.clip(CircleShape).fillMaxSize()
                                )
                            }
                        }
                    }
                },
                headlineContent = {
                    Text(text = beerItem.name)
                },
                supportingContent = {
                    Text(beerItem.tagline)
                },
            )
        }
    }
}

@Composable
private fun SectionHeadline(text: StringResource) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
private fun BeerListComponentPreview() {
    AppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Dashboard(
                favorites = listOf(
                    BeerUIItem(
                        id = 3,
                        name = "item_3",
                        shortDescription = "description 123",
                        imageUrl = null,
                        tagline = "Tag1, Tag2",
                    ),
                ),
                beerList = (4..12).map {
                    BeerUIItem(
                        id = it,
                        name = "item_$it",
                        shortDescription = "description 123",
                        imageUrl = null,
                        tagline = "Tag1, Tag2",
                    )
                },
                randomBeer = null,
                onAction = {},
            )
        }
    }
}
