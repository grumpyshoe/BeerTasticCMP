package com.grumpyshoe.beertasticcmp.presentation.common.resources.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavTarget {
    @Serializable
    object SplashscreenNavTarget : NavTarget()
    @Serializable
    object HomeNavTarget : NavTarget()
    @Serializable
    data class BeerDetailNavTarget(val beerId:Int) : NavTarget()
}