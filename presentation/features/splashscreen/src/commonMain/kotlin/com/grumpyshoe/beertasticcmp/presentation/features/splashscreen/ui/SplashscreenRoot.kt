package com.grumpyshoe.beertasticcmp.presentation.features.splashscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.grumpyshoe.beertasticcmp.presentation.common.resources.AppColors
import com.grumpyshoe.beertasticcmp.presentation.common.resources.AppTheme
import com.grumpyshoe.beertasticcmp.presentation.common.resources.Res
import com.grumpyshoe.beertasticcmp.presentation.common.resources.logo
import com.grumpyshoe.beertasticcmp.presentation.common.resources.splashscreen_author
import com.grumpyshoe.beertasticcmp.presentation.common.resources.splashscreen_showcase_text
import com.grumpyshoe.beertasticcmp.presentation.common.resources.splashscreen_subTitle
import com.grumpyshoe.beertasticcmp.presentation.common.resources.splashscreen_title
import com.grumpyshoe.beertasticcmp.presentation.features.splashscreen.viewmodel.SplashscreenViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun SplashscreenRoot(
    viewModel: SplashscreenViewModel = koinInject(),
    navigateToHome: () -> Unit
) {
    val event by viewModel.events.collectAsStateWithLifecycle(null)

    LaunchedEffect(event) {
        when (event) {
            null -> return@LaunchedEffect
            is SplashscreenEvent.NavigateToHome -> navigateToHome()
        }
    }

    SplashscreenContent()
}

@Composable
fun SplashscreenContent() {

    Box(contentAlignment = Alignment.TopEnd) {
        Box(
            contentAlignment = BiasAlignment(0f, 0f),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(32.dp),
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                        .wrapContentSize()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painterResource(Res.drawable.logo),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier.width(IntrinsicSize.Min),
                            contentAlignment = Alignment.TopEnd,
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.displayLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                text = stringResource(Res.string.splashscreen_title),
                            )
                            Text(
                                modifier = Modifier.graphicsLayer {
                                    translationX = 32f
                                },
                                style = AppTheme.typography.material.labelLarge,
                                color = AppTheme.colors.material.onBackground,
                                text = "2.0",
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = stringResource(Res.string.splashscreen_showcase_text),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = stringResource(Res.string.splashscreen_author),
                    textAlign = TextAlign.Center,
                )
            }
        }

        Text(
            modifier = Modifier
                .width(800.dp)
                .graphicsLayer(
                    translationX = 400f,
                    translationY = 220f,
                    rotationZ = 40f
                )
                .background(AppTheme.colors.splashscreenBanner)
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onError,
            text = stringResource(Res.string.splashscreen_subTitle),
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        SplashscreenContent()
    }
}
