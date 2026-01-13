package com.grumpyshoe.beertasticcmp.presentation.features.home.ui

/**
 * This sealed class contains all action/intents a user can trigger from within the UI.
 */
sealed class HomeAction {
    data object LoadMore: HomeAction()
    data object ShowRandomBeer: HomeAction()
    data object HideRandomBeer: HomeAction()
    data class ShowDetails(val beerId:Int): HomeAction()
}