package com.grumpyshoe.beertasticcmp.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import com.grumpyshoe.beertasticcmp.presentation.common.resources.navigation.NavTarget
import com.grumpyshoe.beertasticcmp.presentation.features.details.ui.DetailsRoot
import com.grumpyshoe.beertasticcmp.presentation.features.home.ui.HomeRoot
import com.grumpyshoe.beertasticcmp.presentation.features.splashscreen.ui.SplashscreenRoot

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavigationGraph() {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val directive = remember(windowAdaptiveInfo) {
        calculatePaneScaffoldDirective(windowAdaptiveInfo)
            .copy(horizontalPartitionSpacerSize = 0.dp)
    }
    val listDetailStrategy = rememberListDetailSceneStrategy<Any>(directive = directive)

    val backStack: MutableList<NavTarget> =
        rememberSerializable(
            serializer = SnapshotStateListSerializer(NavTarget.serializer())
        ) {
            mutableStateListOf<NavTarget>(NavTarget.SplashscreenNavTarget)
        }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        sceneStrategy = listDetailStrategy,
        entryProvider = entryProvider {
            entry<NavTarget.SplashscreenNavTarget> {
                SplashscreenRoot(
                    navigateToHome = {
                        backStack.apply {
                            removeFirstOrNull()
                            add(NavTarget.HomeNavTarget)
                        }
                    }
                )
            }
            entry<NavTarget.HomeNavTarget> {
                HomeRoot(
                    navigateToDetails = { beerId ->
                        backStack.add(
                            NavTarget.BeerDetailNavTarget(beerId)
                        )
                    }
                )
            }

            entry<NavTarget.BeerDetailNavTarget> {
                DetailsRoot(
                    beerId = it.beerId,
                    navigateBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}
