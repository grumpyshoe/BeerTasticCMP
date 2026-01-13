package com.grumpyshoe.beertasticcmp.presentation.features.details.ui

/**
 * This sealed class contains all action/intents a user can trigger from within the UI.
 */
sealed class DetailsAction {
    data object ToggleFavoriteState : DetailsAction()
}