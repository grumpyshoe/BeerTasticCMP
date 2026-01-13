package com.grumpyshoe.beertasticcmp.presentation.features.splashscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grumpyshoe.beertasticcmp.presentation.features.splashscreen.ui.SplashscreenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SplashscreenViewModel : ViewModel() {

    private val _events = MutableSharedFlow<SplashscreenEvent?>()
    val events = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _events.emit(
                SplashscreenEvent.NavigateToHome
            )
        }
    }
}
